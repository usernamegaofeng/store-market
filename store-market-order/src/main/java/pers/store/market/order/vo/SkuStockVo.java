package pers.store.market.order.vo;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午9:36
 * @description:
 */
@Data
public class SkuStockVo {
    private Long skuId;
    private Boolean hasStock;
}
