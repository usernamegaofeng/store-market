package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@ApiModel("相册实体类")
@Table(name = "tb_album")
public class Album implements Serializable {

    @Id
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "相册名称")
    private String title;
    @ApiModelProperty(value = "相册封面")
    private String image;
    @ApiModelProperty(value = "图片列表")
    private String imageItems;

}
