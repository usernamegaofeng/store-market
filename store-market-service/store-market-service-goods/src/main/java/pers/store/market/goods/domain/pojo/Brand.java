package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 高枫
 * @date 2021/1/1 下午7:09
 */
@Data
@Table(name = "tb_brand")
@ApiModel("品牌实体类")
public class Brand implements Serializable {

    @Id
    @ApiModelProperty(value = "品牌id")
    private Integer id;
    @ApiModelProperty(value = "品牌名称")
    private String name;
    @ApiModelProperty(value = "品牌图片地址")
    private String image;
    @ApiModelProperty(value = "品牌的首字母")
    private String letter;
    @ApiModelProperty(value = "排序")
    private Integer seq;
}
