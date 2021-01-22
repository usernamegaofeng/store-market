package pers.store.market.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.member.entity.MemberEntity;

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
}

