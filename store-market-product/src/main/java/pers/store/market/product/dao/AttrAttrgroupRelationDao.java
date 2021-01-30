package pers.store.market.product.dao;

import org.apache.ibatis.annotations.Param;
import pers.store.market.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void removeBatchAttrRelation(@Param("relationEntityList") List<AttrAttrgroupRelationEntity> relationEntityList);
}

