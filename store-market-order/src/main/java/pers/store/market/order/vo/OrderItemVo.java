package pers.store.market.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午6:59
 * @description: 订单项vo
 */
@Data
public class OrderItemVo {

    private Long skuId;

    private Boolean check;

    private String title;

    private String image;

    /**
     * 商品套餐属性
     */
    private List<String> skuAttrValues;

    private BigDecimal price;

    private Integer count;

    private BigDecimal totalPrice;

    /**
     * 商品重量
     **/
    private BigDecimal weight = new BigDecimal("0.085");
}
