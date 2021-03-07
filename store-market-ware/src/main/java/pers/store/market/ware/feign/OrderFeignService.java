package pers.store.market.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.store.market.common.utils.R;

/**
 * @author Gaofeng
 * @date 2021/3/7 下午5:00
 * @description: 订单服务feign接口
 */
@FeignClient("store-order")
public interface OrderFeignService {

    @GetMapping("/order/order/status/{orderSn}")
    R getOrderBySn(@PathVariable("orderSn") String orderSn);
}
