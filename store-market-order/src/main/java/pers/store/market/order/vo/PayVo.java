package pers.store.market.order.vo;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/3/13 上午10:18
 * @description: 支付参数vo
 */
@Data
public class PayVo {

    //商户订单号 必填
    private String out_trade_no;
    //订单名称 必填
    private String subject;
    //付款金额 必填
    private String total_amount;
    //商品描述 可空
    private String body;
}
