package pers.store.market.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.store.market.auth.vo.UserLoginVo;
import pers.store.market.auth.vo.UserRegisterVo;
import pers.store.market.common.domain.vo.SocialUserVo;
import pers.store.market.common.utils.R;

/**
 * @author Gaofeng
 * @date 2021/2/19 下午7:42
 * @description: 会员服务feign接口
 */
@FeignClient("store-member")
public interface MemberFeignService {

    @PostMapping("/member/member/register")
    R register(@RequestBody UserRegisterVo registerVo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo userLoginVo);

    @PostMapping("/member/member/oauth2/login)")
    R login(@RequestBody SocialUserVo socialUserVo);
}
