package pers.store.market.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.store.market.common.utils.R;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午3:31
 * @description: 第三方服务feign接口
 */
@FeignClient("store-third-party")
public interface ThirdPartyFeignService {

    @GetMapping("/sms/sendCode")
    R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
