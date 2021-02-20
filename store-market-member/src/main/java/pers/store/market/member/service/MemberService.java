package pers.store.market.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.domain.vo.SocialUserVo;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.member.entity.MemberEntity;
import pers.store.market.member.vo.UserLoginVo;
import pers.store.market.member.vo.UserRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:39:14
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(UserRegisterVo registerVo);

    MemberEntity login(UserLoginVo userLoginVo);

    MemberEntity login(SocialUserVo socialUser);
}

