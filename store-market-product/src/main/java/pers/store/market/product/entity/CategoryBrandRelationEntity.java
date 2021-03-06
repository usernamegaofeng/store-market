package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 品牌分类关联
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:15
 */
@Data
@TableName("pms_category_brand_relation")
@ApiModel(description = "品牌分类关联实体类")
public class CategoryBrandRelationEntity implements Serializable {

    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value = "ID")
    private Long id;
    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    @NotNull
    private Long brandId;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    @NotNull
    private Long catelogId;
    /**
     * 品牌名称
     */
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String catelogName;

}
