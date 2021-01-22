package pers.store.market.ware.dao;

import pers.store.market.ware.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:22
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
