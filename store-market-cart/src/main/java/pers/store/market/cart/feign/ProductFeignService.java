package pers.store.market.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pers.store.market.common.utils.R;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/26 下午5:29
 * @description: 商品服务feign接口
 */
@FeignClient("store-product")
public interface ProductFeignService {

    @GetMapping(value = "/product/skuInfo/info/{skuId}")
    R getSkuInfo(@PathVariable("skuId") Long skuId);

    @GetMapping("/product/skuSaleAttrValue/getSkuSaleAttrAsStringList")
    List<String> getSkuSaleAttrAsStringList(@RequestParam("skuId") Long skuId);

    @GetMapping("/product/skuInfo/{skuId}getPrice")
    R getCurrentPrice(@PathVariable("skuId") Long skuId);
}
