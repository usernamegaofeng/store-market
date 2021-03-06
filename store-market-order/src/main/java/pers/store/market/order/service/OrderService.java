package pers.store.market.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.order.entity.OrderEntity;
import pers.store.market.order.vo.OrderConfirmVo;
import pers.store.market.order.vo.OrderSubmitVo;
import pers.store.market.order.vo.SubmitOrderResponseVo;

import java.util.Map;

/**
 * 订单
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:42:48
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrders();

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);
}

