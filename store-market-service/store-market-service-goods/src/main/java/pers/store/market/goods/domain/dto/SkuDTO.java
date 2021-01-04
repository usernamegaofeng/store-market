package pers.store.market.goods.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/4 下午2:31
 */
@Data
public class SkuDTO {

    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品条码")
    private String sn;
    @ApiModelProperty(value = "SKU名称")
    private String name;
    @ApiModelProperty(value = "价格（分)")
    private Integer price;
    @ApiModelProperty(value = "库存数量")
    private Integer num;
    @ApiModelProperty(value = "库存预警数量")
    private Integer alertNum;
    @ApiModelProperty(value = "商品图片")
    private String image;
    @ApiModelProperty(value = "商品图片列表")
    private String images;
    @ApiModelProperty(value = "重量（克）")
    private Integer weight;
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
    @ApiModelProperty(value = "SpuID")
    private String spuId;
    @ApiModelProperty(value = "类目ID")
    private Integer categoryId;
    @ApiModelProperty(value = "类目名称")
    private String categoryName;
    @ApiModelProperty(value = "品牌名称")
    private String brandName;
    @ApiModelProperty(value = "规格")
    private Map<String,String> spec;
    @ApiModelProperty(value = "销量")
    private Integer saleNum;
    @ApiModelProperty(value = "评论数")
    private Integer commentNum;
    @ApiModelProperty(value = "商品状态,1-正常，2-下架，3-删除")
    private String status;
}
