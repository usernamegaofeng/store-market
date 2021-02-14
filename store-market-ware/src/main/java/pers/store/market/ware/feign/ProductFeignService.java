package pers.store.market.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.store.market.common.utils.R;

@FeignClient("store-product")
public interface ProductFeignService {

    /**
     * 1)、让所有请求过网关:
     * 1、@FeignClient("store-gateway")：给store-gateway所在的机器发请求
     * 路径:/api/product/skuInfo/info/{skuId}
     * 2）、直接让后台指定服务处理:
     * 1、@FeignClient("store-product")
     * 路径:/product/skuInfo/info/{skuId}
     */
    @RequestMapping("/product/skuInfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
