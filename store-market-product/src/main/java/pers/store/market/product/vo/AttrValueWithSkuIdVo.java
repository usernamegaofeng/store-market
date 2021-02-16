package pers.store.market.product.vo;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午8:36
 * @description: 属性值和sku
 */
@Data
public class AttrValueWithSkuIdVo {

    /**
     * 属性值
     */
    private String attrValue;
    /**
     * skuId
     */
    private String skuIds;
}
