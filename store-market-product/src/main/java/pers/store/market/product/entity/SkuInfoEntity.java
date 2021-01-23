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
 * sku信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:42:14
 */
@Data
@TableName("pms_sku_info")
@ApiModel(description = "sku信息实体类")
public class SkuInfoEntity implements Serializable {

    /**
     * skuId
     */
    @TableId
    @ApiModelProperty(value = "skuId")
    private Long skuId;
    /**
     * spuId
     */
    @ApiModelProperty(value = "spuId")
    private Long spuId;
    /**
     * sku名称
     */
    @ApiModelProperty(value = "sku名称")
    private String skuName;
    /**
     * sku介绍描述
     */
    @ApiModelProperty(value = "sku介绍描述")
    private String skuDesc;
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
    /**
     * 默认图片
     */
    @ApiModelProperty(value = "默认图片")
    private String skuDefaultImg;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String skuTitle;
    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    private String skuSubtitle;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private Long saleCount;

}
