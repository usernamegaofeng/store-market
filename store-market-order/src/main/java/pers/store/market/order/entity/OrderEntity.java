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
 * 订单
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:40:14
 */
@Data
@TableName("oms_order")
@ApiModel(description = "订单实体类")
public class OrderEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * member_id
     */
    @ApiModelProperty(value = "member_id")
    private Long memberId;
    /**
     * 订单号
     */
    @ApiModelProperty(value = "订单号")
    private String orderSn;
    /**
     * 使用的优惠券
     */
    @ApiModelProperty(value = "使用的优惠券")
    private Long couponId;
    /**
     * create_time
     */
    @ApiModelProperty(value = "create_time")
    private Date createTime;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String memberUsername;
    /**
     * 订单总额
     */
    @ApiModelProperty(value = "订单总额")
    private BigDecimal totalAmount;
    /**
     * 应付总额
     */
    @ApiModelProperty(value = "应付总额")
    private BigDecimal payAmount;
    /**
     * 运费金额
     */
    @ApiModelProperty(value = "运费金额")
    private BigDecimal freightAmount;
    /**
     * 促销优化金额（促销价、满减、阶梯价）
     */
    @ApiModelProperty(value = "促销优化金额（促销价、满减、阶梯价）")
    private BigDecimal promotionAmount;
    /**
     * 积分抵扣金额
     */
    @ApiModelProperty(value = "积分抵扣金额")
    private BigDecimal integrationAmount;
    /**
     * 优惠券抵扣金额
     */
    @ApiModelProperty(value = "优惠券抵扣金额")
    private BigDecimal couponAmount;
    /**
     * 后台调整订单使用的折扣金额
     */
    @ApiModelProperty(value = "后台调整订单使用的折扣金额")
    private BigDecimal discountAmount;
    /**
     * 支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】
     */
    @ApiModelProperty(value = "支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】")
    private Integer payType;
    /**
     * 订单来源[0->PC订单；1->app订单]
     */
    @ApiModelProperty(value = "订单来源[0->PC订单；1->app订单]")
    private Integer sourceType;
    /**
     * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
     */
    @ApiModelProperty(value = "订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】")
    private Integer status;
    /**
     * 物流公司(配送方式)
     */
    @ApiModelProperty(value = "物流公司(配送方式)")
    private String deliveryCompany;
    /**
     * 物流单号
     */
    @ApiModelProperty(value = "物流单号")
    private String deliverySn;
    /**
     * 自动确认时间（天）
     */
    @ApiModelProperty(value = "自动确认时间（天）")
    private Integer autoConfirmDay;
    /**
     * 可以获得的积分
     */
    @ApiModelProperty(value = "可以获得的积分")
    private Integer integration;
    /**
     * 可以获得的成长值
     */
    @ApiModelProperty(value = "可以获得的成长值")
    private Integer growth;
    /**
     * 发票类型[0->不开发票；1->电子发票；2->纸质发票]
     */
    @ApiModelProperty(value = "发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    private Integer billType;
    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    private String billHeader;
    /**
     * 发票内容
     */
    @ApiModelProperty(value = "发票内容")
    private String billContent;
    /**
     * 收票人电话
     */
    @ApiModelProperty(value = "收票人电话")
    private String billReceiverPhone;
    /**
     * 收票人邮箱
     */
    @ApiModelProperty(value = "收票人邮箱")
    private String billReceiverEmail;
    /**
     * 收货人姓名
     */
    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;
    /**
     * 收货人电话
     */
    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;
    /**
     * 收货人邮编
     */
    @ApiModelProperty(value = "收货人邮编")
    private String receiverPostCode;
    /**
     * 省份/直辖市
     */
    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;
    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String receiverCity;
    /**
     * 区
     */
    @ApiModelProperty(value = "区")
    private String receiverRegion;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;
    /**
     * 订单备注
     */
    @ApiModelProperty(value = "订单备注")
    private String note;
    /**
     * 确认收货状态[0->未确认；1->已确认]
     */
    @ApiModelProperty(value = "确认收货状态[0->未确认；1->已确认]")
    private Integer confirmStatus;
    /**
     * 删除状态【0->未删除；1->已删除】
     */
    @ApiModelProperty(value = "删除状态【0->未删除；1->已删除】")
    private Integer deleteStatus;
    /**
     * 下单时使用的积分
     */
    @ApiModelProperty(value = "下单时使用的积分")
    private Integer useIntegration;
    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;
    /**
     * 发货时间
     */
    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;
    /**
     * 确认收货时间
     */
    @ApiModelProperty(value = "确认收货时间")
    private Date receiveTime;
    /**
     * 评价时间
     */
    @ApiModelProperty(value = "评价时间")
    private Date commentTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

}
