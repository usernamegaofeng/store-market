package pers.store.market.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午9:23
 * @description: spu的vo, 用于远程调用服务
 * todo 抽取到共用模块
 */
@Data
public class SpuInfoVo {

    /**
     * 商品id
     */
    private Long id;
    /**
     * 商品名称
     */
    private String spuName;
    /**
     * 商品描述
     */
    private String spuDescription;
    /**
     * 所属分类id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;
    private String brandName;
    /**
     *
     */
    private BigDecimal weight;
    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    private Integer publishStatus;
    private Date createTime;
    private Date updateTime;

}
