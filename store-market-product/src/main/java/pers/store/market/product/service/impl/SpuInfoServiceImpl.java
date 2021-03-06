package pers.store.market.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pers.store.market.common.constant.ProductConstant;
import pers.store.market.common.domain.dto.SkuReductionDto;
import pers.store.market.common.domain.dto.SpuBoundDto;
import pers.store.market.common.domain.model.SkuEsModel;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;
import pers.store.market.common.utils.R;
import pers.store.market.product.dao.SpuInfoDao;
import pers.store.market.product.entity.*;
import pers.store.market.product.feign.CouponFeignService;
import pers.store.market.product.feign.SearchFeignService;
import pers.store.market.product.feign.WareFeignService;
import pers.store.market.product.service.*;
import pers.store.market.product.vo.*;

@Slf4j
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private AttrService attrService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SpuImagesService imagesService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private SearchFeignService searchFeignService;
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private CouponFeignService couponFeignService;
    @Autowired
    private ProductAttrValueService attrValueService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;


    /**
     * 条件查询
     *
     * @param params 查询参数
     * @return PageUtils
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("id", key).or().like("spu_name", key);
            });
        }
        // status=1 and (id=1 or spu_name like xxx)
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("publish_status", status);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            wrapper.eq("brand_id", brandId);
        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            wrapper.eq("catalog_id", catelogId);
        }
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);

    }

    /**
     * 保存spu以及sku优惠信息
     *
     * @param vo 前端参数封装实体类
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        //1、保存spu基本信息 pms_spu_info
        SpuInfoEntity infoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, infoEntity);
        infoEntity.setCreateTime(new Date());
        infoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(infoEntity);

        //2、保存Spu的描述图片 pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(infoEntity.getId());
        descEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(descEntity);

        //3、保存spu的图片集 pms_spu_images
        List<String> images = vo.getImages();
        imagesService.saveImages(infoEntity.getId(), images);

        //4、保存spu的规格参数;pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            AttrEntity id = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(id.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(infoEntity.getId());

            return valueEntity;
        }).collect(Collectors.toList());
        attrValueService.saveProductAttr(collect);

        //5、保存spu的积分信息
        Bounds bounds = vo.getBounds();
        SpuBoundDto spuBoundTo = new SpuBoundDto();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(infoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0) {
            log.error("远程保存spu积分信息失败");
        }

        //5、保存当前spu对应的所有sku信息；
        List<Skus> skus = vo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(item -> {
                String defaultImg = "";
                for (Images image : item.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(infoEntity.getBrandId());
                skuInfoEntity.setCatalogId(infoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(infoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoEntity.setSkuDesc(infoEntity.getSpuName());
                //5.1）、sku的基本信息；pms_sku_info
                skuInfoService.saveSkuInfo(skuInfoEntity);
                //skuId
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
                    //返回true就是需要，false就是过滤掉
                    return !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                //5.2）、sku的图片信息；pms_sku_image
                skuImagesService.saveBatch(imagesEntities);
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity attrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, attrValueEntity);
                    attrValueEntity.setSkuId(skuId);
                    return attrValueEntity;
                }).collect(Collectors.toList());
                //5.3）、sku的销售属性信息：pms_sku_sale_attr_value
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                //5.4）、sku的优惠、满减等信息,sms_sku_ladder\sms_sku_full_reduction\sms_member_price
                SkuReductionDto skuReductionDto = new SkuReductionDto();
                BeanUtils.copyProperties(item, skuReductionDto);
                skuReductionDto.setSkuId(skuId);
                if (skuReductionDto.getFullCount() > 0 || skuReductionDto.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    R r1 = couponFeignService.saveSkuReduction(skuReductionDto);
                    if (r1.getCode() != 0) {
                        log.error("远程保存sku优惠信息失败");
                    }
                }
            });
        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity infoEntity) {
        this.baseMapper.insert(infoEntity);
    }

    /**
     * 商品上架
     *
     * @param spuId spuId
     */
    @Transactional
    @Override
    public void up(Long spuId) {
        //查询当前spu对应的所有sku集合
        List<SkuInfoEntity> skuInfoEntityList = skuInfoService.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        ////查询当前spu的所有可以被检索的属性
        List<ProductAttrValueEntity> productAttrValueEntityList = attrValueService.baseAttrListForSpu(spuId);
        List<Long> attrIds = productAttrValueEntityList.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        //可以被检索的属性
        List<Long> searchAttrIds = attrService.getSearchAttrs(attrIds);
        Set<Long> set = new HashSet<Long>(searchAttrIds);
        //过滤掉不能被检索的ProductAttrValueEntity
        List<SkuEsModel.Attrs> attrsList = productAttrValueEntityList.stream().filter(item -> set.contains(item.getAttrId())).map(item -> {
            SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attrs);
            return attrs;
        }).collect(Collectors.toList());

        //调用feign接口查询sku是否有库存
        List<Long> skuIds = skuInfoEntityList.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        //转换成map
        Map<Long, Boolean> dataMap = null;
        try {
            List<SkuHasStockVo> skuHasStockVos = wareFeignService.hasStock(skuIds);
            dataMap = skuHasStockVos.stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
        } catch (Exception e) {
            log.error("调用wareFeignService接口失败,错误原因 ===>{}", e.getMessage());
        }

        //封装SkuEsModel对象
        Map<Long, Boolean> finalDataMap = dataMap;
        List<SkuEsModel> modelList = skuInfoEntityList.stream().map(item -> {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(item, skuEsModel);
            //封装字段
            skuEsModel.setSkuPrice(item.getPrice());
            skuEsModel.setSkuImg(item.getSkuDefaultImg());
            skuEsModel.setHotScore(0L);  //热度评分暂时设置为0
            skuEsModel.setHasStock(finalDataMap.get(item.getSkuId())); //是否有库存
            //查询品牌和分类的名称
            BrandEntity brandEntity = brandService.getById(item.getBrandId());
            CategoryEntity categoryEntity = categoryService.getById(item.getCatalogId());
            skuEsModel.setBrandName(brandEntity.getName());
            skuEsModel.setBrandImg(brandEntity.getLogo());
            skuEsModel.setCatalogName(categoryEntity.getName());
            skuEsModel.setAttrs(attrsList);
            return skuEsModel;
        }).collect(Collectors.toList());

        //调用搜索服务进行索引保存
        try {
            R result = searchFeignService.saveIndex(modelList);
            if (result.getCode() == 0) {
                //修改发布状态
                baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.UP.getCode());
            }
        } catch (Exception e) {
            log.error("调用searchFeignService失败,错误原因 ===> {}", e.getMessage());
        }

    }

    @Override
    public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {
        SkuInfoEntity skuInfoEntity = skuInfoService.getById(skuId);
        if (skuInfoEntity != null) {
            SpuInfoEntity spuInfoEntity = this.getById(skuInfoEntity.getSpuId());
            return spuInfoEntity;
        }
        return null;
    }
}