package pers.store.market.product.vo;

import lombok.Data;
import pers.store.market.product.entity.SkuImagesEntity;
import pers.store.market.product.entity.SkuInfoEntity;
import pers.store.market.product.entity.SpuInfoDescEntity;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午8:31
 * @description: 商品详情页vo
 */
@Data
public class SkuItemVo {

    //1、sku基本信息的获取
    private SkuInfoEntity info;
    private boolean hasStock = true;
    //2、sku的图片信息
    private List<SkuImagesEntity> images;
    //3、获取spu的销售属性组合
    private List<SkuItemSaleAttrVo> saleAttr;
    //4、获取spu的介绍
    private SpuInfoDescEntity desc;
    //5、获取spu的规格参数信息
    private List<SpuItemAttrGroupVo> groupAttrs;
    //6、秒杀商品的优惠信息
    private SeckillSkuVo seckillSkuVo;
}
