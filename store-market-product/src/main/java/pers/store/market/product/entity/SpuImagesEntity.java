package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * spu图片
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Data
@TableName("pms_spu_images")
@ApiModel(description = "spu图片实体类")
public class SpuImagesEntity implements Serializable {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * spu_id
     */
    @ApiModelProperty(value = "spu_id")
    private Long spuId;
    /**
     * 图片名
     */
    @ApiModelProperty(value = "图片名")
    private String imgName;
    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;
    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer imgSort;
    /**
     * 是否默认图
     */
    @ApiModelProperty(value = "是否默认图")
    private Integer defaultImg;

}
