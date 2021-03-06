package pers.store.market.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午8:41
 * @description: 订单数据vo
 */
@Data
public class OrderSubmitVo {

    /**
     * 收获地址的id
     **/
    private Long addrId;

    /**
     * 支付方式
     **/
    private Integer payType;

    //无需提交要购买的商品，去购物车再获取一遍

    /**
     * 防重令牌
     **/
    private String orderToken;

    /**
     * 应付价格
     **/
    private BigDecimal payPrice;

    /**
     * 订单备注
     **/
    private String remarks;
}
