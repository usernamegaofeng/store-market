package pers.store.market.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.store.market.common.utils.R;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午9:57
 * @description:
 */
@FeignClient("store-member")
public interface MemberFeignService {

    @RequestMapping("member/memberReceiveAddress/info/{id}")
    R info(@PathVariable("id") Long id);
}
