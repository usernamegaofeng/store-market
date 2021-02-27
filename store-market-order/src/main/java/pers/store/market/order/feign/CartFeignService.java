package pers.store.market.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.store.market.order.vo.OrderItemVo;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午10:28
 * @description:
 */
@FeignClient("store-cart")
public interface CartFeignService {

    @RequestMapping("/getUserCartItems")
     List<OrderItemVo> getUserCartItems();

}
