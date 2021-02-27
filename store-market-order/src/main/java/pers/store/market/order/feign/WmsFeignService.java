package pers.store.market.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.store.market.common.domain.vo.SkuHasStockVo;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午10:51
 * @description: 库存服务feign接口
 */
@FeignClient("store-ware")
public interface WmsFeignService {

    @PostMapping("/ware/wareSku/hasStock")
    List<SkuHasStockVo> hasStock(@RequestBody List<Long> skuIds);
}
