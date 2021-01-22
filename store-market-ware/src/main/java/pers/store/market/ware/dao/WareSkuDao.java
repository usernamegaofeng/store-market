package pers.store.market.ware.dao;

import pers.store.market.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:46:29
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
