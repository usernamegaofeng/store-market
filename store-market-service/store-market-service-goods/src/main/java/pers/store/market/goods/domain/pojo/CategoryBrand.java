package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;

/**
 * @author 高枫
 * @date 2021/1/2 下午4:49
 * <p>
 * 分类与品牌之间的关系属于多对多关系
 * 分类品牌中间表
 */
@Data
@Table(name = "tb_category_brand")
@ApiModel("分类品牌中间表")
public class CategoryBrand {

    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;
    @ApiModelProperty(value = "品牌ID")
    private Integer brandId;

}
