package pers.store.market.coupon.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pers.store.market.coupon.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:46
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    @Update("UPDATE wms_ware_sku set stock = stock+${skuNum} where sku_id=#{skuId} and ware_id = wareId")
    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    @Select("SELECT sum(stock-stock_locked) FROM wms_ware_sku where sku_id = #{item}")
    Long getStockById(@Param("item") Long item);
}
