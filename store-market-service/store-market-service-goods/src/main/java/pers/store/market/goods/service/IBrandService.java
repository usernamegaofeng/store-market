package pers.store.market.goods.service;

import com.github.pagehelper.Page;
import pers.store.market.goods.domain.pojo.Brand;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/1 下午7:24
 *
 *  品牌服务接口
 */
public interface IBrandService {

    /**
     * 品牌列表查询
     */
    List<Brand> findList();

    /**
     * 根据id查询品牌数据
     */
    Brand findById(Integer id);

    /**
     * 品牌新增
     */
    void add(Brand brand);

    /**
     * 品牌修改
     */
    void update(Brand brand);

    /**
     * 品牌删除
     */
    void delById(Integer id);

    /**
     * 品牌列表条件查询
     */
    List<Brand> list(Map<String,Object> searchMap);

    /**
     * 品牌列表分页查询
     * page:当前的页码
     * size:每页显示多少条
     */
    Page<Brand> findPage(int page,int size);

    /**
     * 品牌列表分页+条件查询
     */
    Page<Brand> findPage(Map<String,Object> searchMap, int page, int size);

}
