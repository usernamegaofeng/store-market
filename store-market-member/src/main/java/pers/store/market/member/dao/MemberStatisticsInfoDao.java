package pers.store.market.member.dao;

import pers.store.market.member.entity.MemberStatisticsInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员统计信息
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:39:14
 */
@Mapper
public interface MemberStatisticsInfoDao extends BaseMapper<MemberStatisticsInfoEntity> {
	
}
