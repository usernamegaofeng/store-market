package pers.store.market.goods.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 属性值、特性相同的货品就可以称为一个SPU
 */
@Data
@ApiModel("spu实体类")
@Table(name = "tb_spu")
public class Spu implements Serializable {

    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;
    @ApiModelProperty(value = "货号")
    private String sn;
    @ApiModelProperty(value = "SPU名称")
    private String name;
    @ApiModelProperty(value = "副标题")
    private String caption;
    @ApiModelProperty(value = "品牌ID")
    private Integer brandId;
    @ApiModelProperty(value = "一级分类")
    private Integer category1Id;
    @ApiModelProperty(value = "二级分类")
    private Integer category2Id;
    @ApiModelProperty(value = "三级分类")
    private Integer category3Id;
    @ApiModelProperty(value = "模板ID")
    private Integer templateId;
    @ApiModelProperty(value = "运费模板id")
    private Integer freightId;
    @ApiModelProperty(value = "图片")
    private String image;
    @ApiModelProperty(value = "图片列表")
    private String images;
    @ApiModelProperty(value = "售后服务")
    private String saleService;
    @ApiModelProperty(value = "介绍")
    private String introduction;
    @ApiModelProperty("规格列表,json格式{\"颜色\":\"蓝\",\"尺码\":\"S\"}")
    private String specItems;
    @ApiModelProperty(value = "参数列表")
    private String paraItems;
    @ApiModelProperty(value = "销量")
    private Integer saleNum;
    @ApiModelProperty(value = "评论数")
    private Integer commentNum;
    @ApiModelProperty(value = "是否上架")
    private String isMarketable;
    @ApiModelProperty(value = "是否启用规格")
    private String isEnableSpec;
    @ApiModelProperty(value = "是否删除")
    private String isDelete;
    @ApiModelProperty(value = "审核状态")
    private String status;


}
