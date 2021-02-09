package pers.store.market.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.store.market.common.domain.vo.SkuHasStockVo;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/9 下午4:03
 * @description: 仓储服务feign服务接口
 */
@FeignClient("store-ware")
public interface WareFeignService {

    @PostMapping("/ware/wareSku/hasStock")
    List<SkuHasStockVo> hasStock(@RequestBody List<Long> skuIds);
}
