package pers.store.market.goods.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.store.market.goods.domain.pojo.Spec;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/2 下午2:57
 */
public interface SpecMapper extends Mapper<Spec> {

    @Select("SELECT name,options FROM tb_spec WHERE template_id IN ( SELECT template_id FROM tb_category WHERE NAME = #{categoryName}) order by seq")
    List<Map> findListByCategoryName(@Param("categoryName") String categoryName);
}
