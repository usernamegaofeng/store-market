package pers.store.market.order.vo;

import lombok.Data;
import pers.store.market.order.entity.OrderEntity;
import pers.store.market.order.entity.OrderItemEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午9:09
 * @description: 订单数据dto
 */
@Data
public class OrderCreateDto {

    private OrderEntity order;
    private List<OrderItemEntity> orderItems;
    private BigDecimal payPrice;
    private BigDecimal fare;
}
