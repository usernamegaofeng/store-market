package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品会员价格
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:43
 */
@Data
@TableName("sms_member_price")
@ApiModel(description = "商品会员价格实体类")
public class MemberPriceEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * sku_id
     */
    @ApiModelProperty(value = "sku_id")
    private Long skuId;
    /**
     * 会员等级id
     */
    @ApiModelProperty(value = "会员等级id")
    private Long memberLevelId;
    /**
     * 会员等级名
     */
    @ApiModelProperty(value = "会员等级名")
    private String memberLevelName;
    /**
     * 会员对应价格
     */
    @ApiModelProperty(value = "会员对应价格")
    private BigDecimal memberPrice;
    /**
     * 可否叠加其他优惠[0-不可叠加优惠，1-可叠加]
     */
    @ApiModelProperty(value = "可否叠加其他优惠[0-不可叠加优惠，1-可叠加]")
    private Integer addOther;

}
