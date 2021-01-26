package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 属性分组
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:15
 */
@Data
@TableName("pms_attr_group")
@ApiModel(description = "属性分组实体类")
public class AttrGroupEntity implements Serializable {

    /**
     * 分组id
     */
    @TableId
    @ApiModelProperty(value = "分组id")
    private Long attrGroupId;
    /**
     * 组名
     */
    @ApiModelProperty(value = "组名")
    private String attrGroupName;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String descript;
    /**
     * 组图标
     */
    @ApiModelProperty(value = "组图标")
    private String icon;
    /**
     * 所属分类id
     */
    @ApiModelProperty(value = "所属分类id")
    private Long catelogId;

    /**
     * 分类完整路径
     */
    @TableField(exist = false)
    private Long[] categoryPath;
}
