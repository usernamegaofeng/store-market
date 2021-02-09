package pers.store.market.coupon.dao;

import pers.store.market.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:22
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {

}
