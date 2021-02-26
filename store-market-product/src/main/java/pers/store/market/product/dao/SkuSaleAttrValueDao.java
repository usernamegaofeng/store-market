package pers.store.market.product.dao;

import org.apache.ibatis.annotations.Param;
import pers.store.market.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.store.market.product.vo.SkuItemSaleAttrVo;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

//    List<SkuItemSaleAttrVo> getSkuSaleAttrValuesAsStringList(@Param("spuId") Long spuId);

    List<SkuItemSaleAttrVo> getSaleAttrBySpuId(@Param("spuId") Long spuId);

    List<String> getSkuSaleAttrAsStringList(@Param("skuId") Long skuId);
}
