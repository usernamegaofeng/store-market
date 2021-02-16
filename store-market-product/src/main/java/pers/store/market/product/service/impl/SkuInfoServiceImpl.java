package pers.store.market.product.service.impl;

import io.netty.util.concurrent.CompleteFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;

import pers.store.market.product.dao.SkuInfoDao;
import pers.store.market.product.entity.SkuImagesEntity;
import pers.store.market.product.entity.SkuInfoEntity;
import pers.store.market.product.entity.SpuInfoDescEntity;
import pers.store.market.product.service.*;
import pers.store.market.product.vo.SkuItemSaleAttrVo;
import pers.store.market.product.vo.SkuItemVo;
import pers.store.market.product.vo.SpuItemAttrGroupVo;


@Slf4j
@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("sku_id", key).or().like("sku_name", key);
            });
        }

        String categoryId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(categoryId) && !"0".equalsIgnoreCase(categoryId)) {

            queryWrapper.eq("catalog_id", categoryId);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(categoryId)) {
            queryWrapper.eq("brand_id", brandId);
        }
        //最小价格
        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min)) {
            queryWrapper.ge("price", min);
        }
        //最大价格
        String max = (String) params.get("max");
        if (!StringUtils.isEmpty(max)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(max);
                if (bigDecimal.compareTo(new BigDecimal("0")) == 1) {
                    queryWrapper.le("price", max);
                }
            } catch (Exception e) {
                log.error("BigDecimal转换异常 ==> {}", e.getMessage());
            }
        }
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.save(skuInfoEntity);
    }

    /**
     * 获取sku详情页数据
     * 使用CompletableFuture进行异步编排
     *
     * @param skuId skuId
     * @return 详情页数据
     */
    @Override
    public SkuItemVo itemInfo(Long skuId) {
        //封装数据
        SkuItemVo skuItemVo = new SkuItemVo();
        //获取sku详情
        CompletableFuture<SkuInfoEntity> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfoEntity info = this.getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, threadPoolExecutor);
        //串行化处理任务
        //获取spu介绍数据
        CompletableFuture<Void> spuDescFuture = skuInfoFuture.thenAcceptAsync((result -> {
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(result.getSpuId());
            skuItemVo.setDesc(spuInfoDescEntity);
        }), threadPoolExecutor);

        //获取spu的销售属性数据
        CompletableFuture<Void> saleAttrsFuture = skuInfoFuture.thenAcceptAsync(result -> {
            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrBySpuId(result.getSpuId());
            skuItemVo.setSaleAttr(saleAttrVos);
        }, threadPoolExecutor);

        //获取spu的规格参数信息
        CompletableFuture<Void> baseAttrsFuture = skuInfoFuture.thenAcceptAsync(result -> {
            List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(result.getSpuId(), result.getCatalogId());
            skuItemVo.setGroupAttrs(attrGroupVos);
        }, threadPoolExecutor);

        //获取sku图片信息,无返回值
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(imagesEntities);
        }, threadPoolExecutor);

        //异步编排,get()方法阻塞等待,必须都执行完成
        try {
            CompletableFuture.allOf(spuDescFuture, saleAttrsFuture, baseAttrsFuture, imageFuture).get();
        } catch (InterruptedException e) {
            log.error("异步编排出现错误 ===> {}", e.getMessage());
        } catch (ExecutionException e) {
            log.error("异步编排出现错误 ===> {}", e.getMessage());
        }
        return skuItemVo;
    }

}