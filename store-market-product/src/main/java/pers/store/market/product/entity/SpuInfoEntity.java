package pers.store.market.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * spu信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Data
@TableName("pms_spu_info")
@ApiModel(description = "spu信息实体类")
public class SpuInfoEntity implements Serializable {

    /**
     * 商品id
     */
    @TableId
    @ApiModelProperty(value = "商品id")
    private Long id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String spuName;
    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    private String spuDescription;
    /**
     * 所属分类id
     */
    @ApiModelProperty(value = "所属分类id")
    private Long catalogId;
    /**
     * 品牌id
     */
    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private BigDecimal weight;
    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    @ApiModelProperty(value = "上架状态[0 - 下架，1 - 上架]")
    private Integer publishStatus;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Date updateTime;

}
