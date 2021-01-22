package pers.store.market.product.dao;

import pers.store.market.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
	
}
