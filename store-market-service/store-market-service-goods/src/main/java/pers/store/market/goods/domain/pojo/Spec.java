package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 高枫
 * @date 2021/1/2 下午4:41
 *
 *  模板和规格是一对多关系
 */
@Data
@Table(name = "tb_spec")
@ApiModel("规格实体类")
public class Spec implements Serializable {

    @Id
    @ApiModelProperty(value = "规格ID")
    private Integer id;
    @ApiModelProperty(value = "规格名称")
    private String name;
    @ApiModelProperty(value = "规格选项")
    private String options;
    @ApiModelProperty(value = "排序")
    private Integer seq;
    @ApiModelProperty(value = "模板ID")
    private Integer templateId;
}
