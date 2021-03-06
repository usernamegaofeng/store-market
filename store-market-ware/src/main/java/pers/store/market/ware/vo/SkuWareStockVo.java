package pers.store.market.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/3/6 下午9:54
 * @description: 库存锁定vo
 */
@Data
public class SkuWareStockVo {

    private Long skuId;
    private List<Long> wareId;
    private Integer num;
}
