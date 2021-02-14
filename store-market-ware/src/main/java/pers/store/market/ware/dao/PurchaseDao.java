package pers.store.market.ware.dao;

import pers.store.market.ware.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:45
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
