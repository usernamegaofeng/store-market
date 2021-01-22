package pers.store.market.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.member.entity.MemberReceiveAddressEntity;

import java.util.Map;

/**
 * 会员收货地址
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:39:14
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

