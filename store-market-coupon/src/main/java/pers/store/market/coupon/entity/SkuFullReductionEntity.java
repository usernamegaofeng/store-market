package pers.store.market.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品满减信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:42
 */
@Data
@TableName("sms_sku_full_reduction")
@ApiModel(description = "商品满减信息实体类")
public class SkuFullReductionEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * spu_id
     */
    @ApiModelProperty(value = "spu_id")
    private Long skuId;
    /**
     * 满多少
     */
    @ApiModelProperty(value = "满多少")
    private BigDecimal fullPrice;
    /**
     * 减多少
     */
    @ApiModelProperty(value = "减多少")
    private BigDecimal reducePrice;
    /**
     * 是否参与其他优惠
     */
    @ApiModelProperty(value = "是否参与其他优惠")
    private Integer addOther;

}
