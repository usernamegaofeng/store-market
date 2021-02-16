package pers.store.market.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午8:35
 * @description: sku详情页vo
 */
@Data
public class SkuItemSaleAttrVo {

    /**
     * 属性ID
     */
    private Long attrId;
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 属性值和属性ID的vo
     */
    private List<AttrValueWithSkuIdVo> attrValues;

}
