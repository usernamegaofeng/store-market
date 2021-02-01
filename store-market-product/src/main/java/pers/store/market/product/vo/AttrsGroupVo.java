package pers.store.market.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pers.store.market.product.entity.AttrEntity;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/1 下午8:23
 * @description: 属性分组和属性组合实体类
 */
@Data
@ApiModel(value = "AttrsGroupVo", description = "属性分组和属性组合实体类")
public class AttrsGroupVo {

    @ApiModelProperty(value = "分组id")
    private Long attrGroupId;
    @ApiModelProperty(value = "组名")
    private String attrGroupName;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "描述")
    private String descript;
    @ApiModelProperty(value = "组图标")
    private String icon;
    @ApiModelProperty(value = "所属分类id")
    private Long catelogId;
    @ApiModelProperty(value = "分组下的所有属性集合")
    private List<AttrEntity> attrs;

}
