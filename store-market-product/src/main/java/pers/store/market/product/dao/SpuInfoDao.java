package pers.store.market.product.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import pers.store.market.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    @Update("UPDATE pms_spu_info set publish_status = #{status},update_time = NOW() WHERE id = #{spuId}")
    void updateSpuStatus(@Param("spuId") Long spuId, @Param("status") int status);
}
