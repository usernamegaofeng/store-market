package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 高枫
 * @date 2021/1/2 下午4:34
 */
@Data
@Table(name = "tb_category")
@ApiModel("分类实体类")
public class Category implements Serializable {

    @Id
    @ApiModelProperty(value = "分类id")
    private Integer id;
    @ApiModelProperty(value = "分类名称")
    private String name;
    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;
    @ApiModelProperty(value = "是否显示")
    private String isShow;
    @ApiModelProperty(value = "是否导航")
    private String isMenu;
    @ApiModelProperty(value = "排序")
    private Integer seq;
    @ApiModelProperty(value = "上级ID")
    private Integer parentId;
    @ApiModelProperty(value = "模板ID")
    private Integer templateId;


}
