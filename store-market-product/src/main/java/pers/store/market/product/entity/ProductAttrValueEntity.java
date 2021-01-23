package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * spu属性值
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Data
@TableName("pms_product_attr_value")
@ApiModel(description = "spu属性值实体类")
public class ProductAttrValueEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private Long spuId;
    /**
     * 属性id
     */
    @ApiModelProperty(value = "属性id")
    private Long attrId;
    /**
     * 属性名
     */
    @ApiModelProperty(value = "属性名")
    private String attrName;
    /**
     * 属性值
     */
    @ApiModelProperty(value = "属性值")
    private String attrValue;
    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer attrSort;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】
     */
    @ApiModelProperty(value = "快速展示【是否展示在介绍上；0-否 1-是】")
    private Integer quickShow;

}
