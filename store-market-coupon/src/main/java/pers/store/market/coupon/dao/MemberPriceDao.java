package pers.store.market.coupon.dao;

import pers.store.market.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:23
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {

}
