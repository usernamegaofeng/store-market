package pers.store.market.order.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午9:34
 * @description: 库存锁定vo
 */
@Data
public class WareSkuLockVo {

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 需要锁住的所有库存信息
     **/
    private List<OrderItemVo> locks;

}
