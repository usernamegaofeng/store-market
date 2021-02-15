package pers.store.market.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.store.market.common.domain.model.SkuEsModel;
import pers.store.market.common.domain.vo.AttrVo;
import pers.store.market.common.utils.R;
import pers.store.market.search.config.ElasticSearchConfig;
import pers.store.market.search.constant.ElasticSearchIndexConstant;
import pers.store.market.search.feign.ProductFeignService;
import pers.store.market.search.service.SearchService;
import pers.store.market.search.vo.SearchParam;
import pers.store.market.search.vo.SearchResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gaofeng
 * @date 2021/2/14 下午7:59
 * @description: 首页搜索接口
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 首页搜索
     *
     * @param searchParam 搜索条件
     * @return SearchResult
     */
    @Override
    public SearchResult search(SearchParam searchParam) {
        //动态构建出查询需要的DSL语句
        SearchRequest searchRequest = null;
        //准备检索请求
        searchRequest = buildSearchRequest(searchParam);
        SearchResult searchResult = null;
        try {
            //执行请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            searchResult = buildSearchResult(searchResponse, searchParam);
        } catch (IOException e) {
            log.error("查询索引库失败,错误原因 ===> {}", e.getMessage());
        }
        return searchResult;
    }

    //构建检索请求
    private SearchRequest buildSearchRequest(SearchParam searchParam) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构建bool-query多条件查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //关键字查询
        if (StringUtils.isNotBlank(searchParam.getKeyword())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword()));
        }
        //品牌ID查询
        if (searchParam.getBrandId() != null && searchParam.getBrandId().size() > 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", searchParam.getBrandId()));
        }
        //分类ID查询
        if (searchParam.getCatalog3Id() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("catalogId", searchParam.getCatalog3Id()));
        }
        //是否库存筛选
        if (searchParam.getHasStock() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 1));
        }
        //attr属性查询
        if (searchParam.getAttrs() != null && searchParam.getAttrs().size() > 0) {
            List<String> attrs = searchParam.getAttrs();
            for (String attr : attrs) {
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

                //格式:attrs=1_5寸:8寸
                String[] str = attr.split("_");
                String attrId = str[0];
                String[] attrValues = str[1].split(":");//这个属性检索用的值
                boolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                boolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues)); //terms可以放数组,多条件查询
                //使用嵌套对象nested查询
                NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("attrs", boolQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQueryBuilder);
            }
        }
        //价格检索
        if (StringUtils.isNotBlank(searchParam.getSkuPrice())) {
            //skuPrice形式为：1_500或_500或500_
            String skuPrice = searchParam.getSkuPrice();
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            String[] split = skuPrice.split("_");
            if (split.length == 1) {
                if (skuPrice.startsWith("_")) {
                    rangeQuery.lte(split[1]);
                } else {
                    rangeQuery.gte(split[0]);
                }
            } else if (split.length == 2) {
                rangeQuery.gte(split[0]).lte(split[1]);
            }
        }
        //封装所有的查询条件
        searchSourceBuilder.query(boolQueryBuilder);

        //封装排序条件
        if (StringUtils.isNotBlank(searchParam.getSort())) {
            String sort = searchParam.getSort();
            String[] split = sort.split("_");
            //构建排序条件
            SortOrder sortOrder = "asc".equalsIgnoreCase(split[1]) ? SortOrder.ASC : SortOrder.DESC;
            searchSourceBuilder.sort(split[0], sortOrder);
        }
        //关键字高亮
        if (StringUtils.isNotBlank(searchParam.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");  //前缀
            highlightBuilder.postTags("</b>");                  //后缀
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        //封装分页条件,当前页码
        int pageNum = (searchParam.getPageNum() - 1) * ElasticSearchIndexConstant.PRODUCT_PAGE_SIZE;
        searchSourceBuilder.from(pageNum);
        searchSourceBuilder.size(ElasticSearchIndexConstant.PRODUCT_PAGE_SIZE);

        //聚合分析,返回搜索栏动态内容
        //按照品牌聚合分析
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brandAgg").field("brandId").size(50);
        //品牌的子聚合-品牌名称聚合
        brandAgg.subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName").size(1));
        //品牌的子聚合-品牌图片聚合
        brandAgg.subAggregation(AggregationBuilders.terms("brandImgAgg").field("brandImg").size(1));
        searchSourceBuilder.aggregation(brandAgg);

        //按照分类聚合分析
        TermsAggregationBuilder categoryAgg = AggregationBuilders.terms("categoryAgg").field("catalogId").size(20);
        //分类的子聚合-品牌名称聚合
        categoryAgg.subAggregation(AggregationBuilders.terms("categoryNameAgg").field("catalogName").size(1));
        searchSourceBuilder.aggregation(categoryAgg);

        //按照属性聚合分析,使用嵌套对象nested查询
        NestedAggregationBuilder attrAgg = AggregationBuilders.nested("attrAgg", "attrs");
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attrIdAgg").field("attrs.attrId");
        //在每个属性ID下，按照属性名进行聚合
        attrIdAgg.subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName")).size(1);
        //在每个属性ID下，按照属性值进行聚合
        attrIdAgg.subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue").size(100));
        attrAgg.subAggregation(attrIdAgg);
        searchSourceBuilder.aggregation(attrAgg);

        log.info("构建的DSL语句 ===> {}", searchSourceBuilder.toString());

        SearchRequest searchRequest = new SearchRequest(new String[]{ElasticSearchIndexConstant.PRODUCT_INDEX}, searchSourceBuilder);
        return searchRequest;
    }

    //构建返回结果集
    private SearchResult buildSearchResult(SearchResponse searchResponse, SearchParam searchParam) {
        SearchResult result = new SearchResult();
        SearchHits hits = searchResponse.getHits();
        //获取所有商品信息
        List<SkuEsModel> products = new ArrayList<>();
        if (hits.getHits() != null && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                //判断是否按关键字检索，若是就显示高亮，否则不显示
                if (StringUtils.isNotBlank(searchParam.getKeyword())) {
                    String skuTitle = hit.getHighlightFields().get("skuTitle").toString();
                    skuEsModel.setSkuTitle(skuTitle);
                }
                products.add(skuEsModel);
            }
        }
        result.setProduct(products);

        //品牌数据封装
        List<SearchResult.BrandVo> brands = new ArrayList<>();
        ParsedLongTerms brandAgg = searchResponse.getAggregations().get("brandAgg");
        for (Terms.Bucket bucket : brandAgg.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            long brandId = bucket.getKeyAsNumber().longValue();
            brandVo.setBrandId(brandId);
            //品牌名称
            ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brandNameAgg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandName(brandName);
            //品牌图片
            ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brandImgAgg");
            String brandImg = brandImgAgg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandImg(brandImg);
            brands.add(brandVo);
        }
        result.setBrands(brands);
        //分类数据封装
        List<SearchResult.CatalogVo> catalogVoList = new ArrayList<>();
        ParsedLongTerms categoryAgg = searchResponse.getAggregations().get("categoryAgg");
        for (Terms.Bucket bucket : categoryAgg.getBuckets()) {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            long categoryId = Long.parseLong(bucket.getKeyAsString());
            catalogVo.setCatalogId(categoryId);
            //分类名称
            ParsedStringTerms categoryNameAgg = bucket.getAggregations().get("categoryNameAgg");
            String categoryName = categoryNameAgg.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(categoryName);
            catalogVoList.add(catalogVo);
        }
        result.setCatalogs(catalogVoList);
        //获取属性信息的聚合
        List<SearchResult.AttrVo> attrs = new ArrayList<>();
        //嵌套结果
        ParsedNested attrAgg = searchResponse.getAggregations().get("attrAgg");
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attrIdAgg");
        for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            //获取属性ID
            long attrId = bucket.getKeyAsNumber().longValue();
            attrVo.setAttrId(attrId);
            //获取属性名称
            ParsedStringTerms attrNameAgg = bucket.getAggregations().get("attrNameAgg");
            String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
            attrVo.setAttrName(attrName);
            //获取属性值
            ParsedStringTerms attrValueAgg = bucket.getAggregations().get("attrValueAgg");
            List<String> attrValueList = attrValueAgg.getBuckets().stream().map(item -> item.getKeyAsString()).collect(Collectors.toList());
            attrVo.setAttrValue(attrValueList);
            attrs.add(attrVo);
        }
        result.setAttrs(attrs);
        result.setPageNum(searchParam.getPageNum());
        //总记录数
        long total = hits.getTotalHits().value;
        result.setTotal(total);
        //计算总页数
        int totalPages = (int) total / ElasticSearchIndexConstant.PRODUCT_PAGE_SIZE == 0 ? (int) total / ElasticSearchIndexConstant.PRODUCT_PAGE_SIZE : ((int) total / ElasticSearchIndexConstant.PRODUCT_PAGE_SIZE) + 1;
        result.setTotalPages(totalPages);
        //分页页码集合
        List<Integer> pageNavs = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNavs.add(i);
        }
        result.setPageNavs(pageNavs);

        //构建面包屑导航
        if (searchParam.getAttrs() != null && searchParam.getAttrs().size() > 0) {
            List<SearchResult.NavVo> collect = searchParam.getAttrs().stream().map(attr -> {
                //1、分析每一个attrs传过来的参数值
                SearchResult.NavVo navVo = new SearchResult.NavVo();
                String[] s = attr.split("_");
                navVo.setNavValue(s[1]);
                R r = productFeignService.attrInfo(Long.parseLong(s[0]));
                if (r.getCode() == 0) {
                    AttrVo data = (AttrVo) r.get("attr");
                    navVo.setNavName(data.getAttrName());
                } else {
                    navVo.setNavName(s[0]);
                }
                //如果取消了这个面包屑以后，我们要跳转到哪个地方，将请求的地址url里面的当前置空
                //拿到所有的查询条件，去掉当前的属性条件
                String encode = null;
                try {
                    encode = URLEncoder.encode(attr, "UTF-8");
                    encode.replace("+", "%20");  //浏览器对空格的编码和Java不一样，差异化处理
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String replace = searchParam.get_queryString().replace("&attrs=" + attr, "");
                navVo.setLink("http://localhost:9500/list.html?" + replace);
                return navVo;
            }).collect(Collectors.toList());
            result.setNavs(collect);
        }
        return result;
    }
}
