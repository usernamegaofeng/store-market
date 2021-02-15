package pers.store.market.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.store.market.common.utils.R;

/**
 * @author Gaofeng
 * @date 2021/2/15 下午6:08
 * @description: 商品服务feign接口
 */
@FeignClient("store-product")
public interface ProductFeignService {

    @GetMapping("/product/attr/info/{attrId}")
    R attrInfo(@PathVariable("attrId") Long attrId);
}
