package pers.store.market.order.dao;

import pers.store.market.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:42:48
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
