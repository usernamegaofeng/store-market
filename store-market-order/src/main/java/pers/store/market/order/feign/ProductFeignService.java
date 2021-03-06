package pers.store.market.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.store.market.common.utils.R;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午9:25
 * @description: 商品服务feign接口
 */
@FeignClient("store-product")
public interface ProductFeignService {

    @GetMapping("/product/spuInfo/getSpuInfo/{skuId}")
    R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId);
}
