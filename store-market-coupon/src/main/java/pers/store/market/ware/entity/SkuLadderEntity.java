package pers.store.market.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品阶梯价格
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:37:42
 */
@Data
@TableName("sms_sku_ladder")
@ApiModel(description = "商品阶梯价格实体类")
public class SkuLadderEntity implements Serializable {

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
     * 满几件
     */
    @ApiModelProperty(value = "满几件")
    private Integer fullCount;
    /**
     * 打几折
     */
    @ApiModelProperty(value = "打几折")
    private BigDecimal discount;
    /**
     * 折后价
     */
    @ApiModelProperty(value = "折后价")
    private BigDecimal price;
    /**
     * 是否叠加其他优惠[0-不可叠加，1-可叠加]
     */
    @ApiModelProperty(value = "是否叠加其他优惠[0-不可叠加，1-可叠加]")
    private Integer addOther;

}
