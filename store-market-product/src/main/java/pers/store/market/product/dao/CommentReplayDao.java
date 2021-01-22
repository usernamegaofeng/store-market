package pers.store.market.product.dao;

import pers.store.market.product.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:44
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}
