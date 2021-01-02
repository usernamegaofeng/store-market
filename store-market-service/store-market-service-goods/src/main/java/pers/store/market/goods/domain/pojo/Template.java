package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 高枫
 * @date 2021/1/2 下午4:39
 */
@Data
@Table(name = "tb_template")
@ApiModel("模板实体类")
public class Template implements Serializable {

    @Id
    @ApiModelProperty(value = "模板ID")
    private Integer id;
    @ApiModelProperty(value = "模板名称")
    private String name;
    @ApiModelProperty(value = "规格数量")
    private Integer specNum;
    @ApiModelProperty(value = "规格参数数量")
    private Integer paraNum;
}
