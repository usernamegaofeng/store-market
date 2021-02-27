package pers.store.market.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.store.market.order.vo.MemberAddressVo;

import java.util.List;


/**
 * @author Gaofeng
 * @date 2021/2/27 下午10:04
 * @description: 会员服务feign接口
 */
@FeignClient("store-member")
public interface MemberFeignService {

    /**
     * 获取会员的所有收获地址
     *
     * @param id 会员ID
     * @return
     */
    @GetMapping("/member/memberReceiveAddress/{id}/getAddress")
    List<MemberAddressVo> getAddress(@PathVariable("id") Long id);
}
