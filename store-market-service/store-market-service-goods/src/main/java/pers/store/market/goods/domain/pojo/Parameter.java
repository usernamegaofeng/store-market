package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 高枫
 * @date 2021/1/2 下午4:44
 * <p>
 * 模板和参数是一对多关系
 */
@Data
@Table(name = "tb_para")
@ApiModel("规格实体类")
public class Parameter implements Serializable {

    @Id
    @ApiModelProperty(value = "参数ID")
    private Integer id;
    @ApiModelProperty(value = "参数名称")
    private String name;
    @ApiModelProperty(value = "参数选项")
    private String options;
    @ApiModelProperty(value = "排序")
    private Integer seq;
    @ApiModelProperty(value = "模板ID")
    private Integer templateId;
}
