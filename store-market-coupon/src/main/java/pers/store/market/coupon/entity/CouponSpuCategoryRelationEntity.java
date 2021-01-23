package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 优惠券分类关联
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:43
 */
@Data
@TableName("sms_coupon_spu_category_relation")
@ApiModel(description = "优惠券分类关联实体类")
public class CouponSpuCategoryRelationEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private Long couponId;
    /**
     * 产品分类id
     */
    @ApiModelProperty(value = "产品分类id")
    private Long categoryId;
    /**
     * 产品分类名称
     */
    @ApiModelProperty(value = "产品分类名称")
    private String categoryName;

}
