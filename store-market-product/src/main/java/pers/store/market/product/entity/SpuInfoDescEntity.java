package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * spu信息介绍
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Data
@TableName("pms_spu_info_desc")
@ApiModel(description = "spu信息介绍实体类")
public class SpuInfoDescEntity implements Serializable {

    /**
     * 商品id
     */
    @TableId(type = IdType.INPUT)   //手动设置输入ID,不是自动增长的ID
    @ApiModelProperty(value = "商品id")
    private Long spuId;
    /**
     * 商品介绍
     */
    @ApiModelProperty(value = "商品介绍")
    private String decript;

}
