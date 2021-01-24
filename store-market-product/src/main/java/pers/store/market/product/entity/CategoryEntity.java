package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品三级分类
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:15
 */
@Data
@TableName("pms_category")
@ApiModel(description = "商品三级分类实体类")
public class CategoryEntity implements Serializable {

    /**
     * 分类id
     */
    @TableId
    @ApiModelProperty(value = "分类id")
    private Long catId;
    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String name;
    /**
     * 父分类id
     */
    @ApiModelProperty(value = "父分类id")
    private Long parentCid;
    /**
     * 层级
     */
    @ApiModelProperty(value = "层级")
    private Integer catLevel;
    /**
     * 是否显示[0-不显示，1显示]
     */
    @ApiModelProperty(value = "是否显示[0-不显示，1显示]")
    private Integer showStatus;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 图标地址
     */
    @ApiModelProperty(value = "图标地址")
    private String icon;
    /**
     * 计量单位
     */
    @ApiModelProperty(value = "计量单位")
    private String productUnit;
    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer productCount;

    /**
     * ,父类下的子类分类,不需要持久化到数据库的字段
     */
    @TableField(exist = false)
    private List<CategoryEntity> childrenList;
}
