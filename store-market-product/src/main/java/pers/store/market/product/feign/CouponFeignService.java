package pers.store.market.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.store.market.common.domain.dto.SkuReductionDto;
import pers.store.market.common.domain.dto.SpuBoundDto;
import pers.store.market.common.utils.R;

/**
 * 优惠服务远程调用feign
 */
@FeignClient("store-coupon")
public interface CouponFeignService {


    @PostMapping("/coupon/spuBounds/save")
    R saveSpuBounds(@RequestBody SpuBoundDto spuBoundTo);


    @PostMapping("/coupon/skuFullReduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionDto skuReductionTo);
}
