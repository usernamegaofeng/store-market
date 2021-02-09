package pers.store.market.common.domain.vo;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午2:28
 * @description: 库存参数vo
 */
@Data
public class SkuHasStockVo {

    private Long skuId;
    private Boolean hasStock;
}
