package pers.store.market.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/1/30 下午3:52
 * @description: 属性与分组的关联参数组合实体类
 */
@Data
@ApiModel(value = "AttrRelationVo", description = "属性与分组的关联参数组合实体类")
public class AttrRelationVo {

    @ApiModelProperty(value = "属性ID")
    private Long attrId;
    @ApiModelProperty(value = "属性分组ID")
    private Long attrGroupId;
}
