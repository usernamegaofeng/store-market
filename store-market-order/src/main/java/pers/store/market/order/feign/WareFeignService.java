package pers.store.market.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.utils.R;
import pers.store.market.order.vo.FareVo;
import pers.store.market.order.vo.WareSkuLockVo;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午10:51
 * @description: 库存服务feign接口
 */
@FeignClient("store-ware")
public interface WareFeignService {

    @PostMapping("/ware/wareSku/hasStock")
    List<SkuHasStockVo> hasStock(@RequestBody List<Long> skuIds);

    @PostMapping(value = "/ware/wareSku/lock/order")
    R orderLockStock(@RequestBody WareSkuLockVo vo);

    @RequestMapping("/ware/wareInfo/fare/{addrId}")
    FareVo getFare(@PathVariable("addrId") Long addrId);
}
