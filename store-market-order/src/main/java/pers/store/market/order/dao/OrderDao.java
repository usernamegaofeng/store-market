package pers.store.market.order.dao;

import pers.store.market.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:42:48
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
