package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 属性值、特性不同的货品就可以称为一个SKU
 */
@Data
@ApiModel("sku实体类")
@Table(name = "tb_sku")
public class Sku implements Serializable {

    @Id
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
    private String spec;
    @ApiModelProperty(value = "销量")
    private Integer saleNum;
    @ApiModelProperty(value = "评论数")
    private Integer commentNum;
    @ApiModelProperty(value = "商品状态,1-正常，2-下架，3-删除")
    private String status;


}
