package pers.store.market.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 支付信息表
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:40:14
 */
@Data
@TableName("oms_payment_info")
@ApiModel(description = "支付信息表实体类")
public class PaymentInfoEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 订单号（对外业务号）
     */
    @ApiModelProperty(value = "订单号（对外业务号）")
    private String orderSn;
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;
    /**
     * 支付宝交易流水号
     */
    @ApiModelProperty(value = "支付宝交易流水号")
    private String alipayTradeNo;
    /**
     * 支付总金额
     */
    @ApiModelProperty(value = "支付总金额")
    private BigDecimal totalAmount;
    /**
     * 交易内容
     */
    @ApiModelProperty(value = "交易内容")
    private String subject;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    private String paymentStatus;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 确认时间
     */
    @ApiModelProperty(value = "确认时间")
    private Date confirmTime;
    /**
     * 回调内容
     */
    @ApiModelProperty(value = "回调内容")
    private String callbackContent;
    /**
     * 回调时间
     */
    @ApiModelProperty(value = "回调时间")
    private Date callbackTime;

}
