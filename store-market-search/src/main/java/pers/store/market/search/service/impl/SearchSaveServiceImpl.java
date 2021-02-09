package pers.store.market.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.store.market.common.domain.model.SkuEsModel;
import pers.store.market.search.config.ElasticSearchConfig;
import pers.store.market.search.constant.ElasticSearchIndexConstant;
import pers.store.market.search.service.SearchSaveService;

import java.io.IOException;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午4:27
 * @description: 索引接口实现类
 */
@Slf4j
@Service
public class SearchSaveServiceImpl implements SearchSaveService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 保存索引
     *
     * @param skuEsModelList 数据集合
     */
    @Override
    public boolean saveIndex(List<SkuEsModel> skuEsModelList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        //封装数据,构造请求
        for (SkuEsModel skuEsModel : skuEsModelList) {
            IndexRequest indexRequest = new IndexRequest(ElasticSearchIndexConstant.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String jsonString = JSON.toJSONString(skuEsModel);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        //批量操作
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        //todo 批量错误需手动处理
        return bulk.hasFailures();

    }
}
