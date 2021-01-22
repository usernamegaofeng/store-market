package pers.store.market.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:39:14
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

