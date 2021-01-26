package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pers.store.market.common.valid.ListValue;
import pers.store.market.common.valid.UpdateGroup;

import javax.validation.constraints.NotNull;

/**
 * 品牌
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:15
 */
@Data
@TableName("pms_brand")
@ApiModel(description = "品牌实体类")
public class BrandEntity implements Serializable {

    /**
     * 品牌id
     */
    @TableId
    @ApiModelProperty(value = "品牌id")
    @NotNull(message = "品牌id不能为空",groups = UpdateGroup.class)
    private Long brandId;
    /**
     * 品牌名
     */
    @ApiModelProperty(value = "品牌名")
    private String name;
    /**
     * 品牌logo地址
     */
    @ApiModelProperty(value = "品牌logo地址")
    private String logo;
    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @ApiModelProperty(value = "显示状态[0-不显示；1-显示]")
    @ListValue(vals = {0,1})  //自定义注解
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @ApiModelProperty(value = "检索首字母")
    private String firstLetter;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
