package pers.store.market.goods.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.store.market.goods.domain.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/1 下午7:20
 */
public interface BrandMapper extends Mapper<Brand> {

    @Select("SELECT name,image FROM tb_brand where id in( SELECT brand_id FROM tb_category_brand WHERE category_id in ( SELECT id from tb_category where name=#{categoryName}))")
    List<Map> findBrandListByCategoryName(@Param("categoryName") String categoryName);
}
