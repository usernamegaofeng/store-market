package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * sku销售属性&值
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Data
@TableName("pms_sku_sale_attr_value")
@ApiModel(description = "sku销售属性&值实体类")
public class SkuSaleAttrValueEntity implements Serializable {

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
     * attr_id
     */
    @ApiModelProperty(value = "attr_id")
    private Long attrId;
    /**
     * 销售属性名
     */
    @ApiModelProperty(value = "销售属性名")
    private String attrName;
    /**
     * 销售属性值
     */
    @ApiModelProperty(value = "销售属性值")
    private String attrValue;
    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer attrSort;

}
