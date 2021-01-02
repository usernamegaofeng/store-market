package pers.store.market.goods.service;

import com.github.pagehelper.Page;
import pers.store.market.goods.domain.pojo.Spec;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/2 下午5:03
 *
 *   规格服务接口
 */
public interface ISpecService {

    /***
     * 查询所有
     * @return
     */
    List<Spec> findAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Spec findById(Integer id);

    /***
     * 新增
     * @param spec
     */
    void add(Spec spec);

    /***
     * 修改
     * @param spec
     */
    void update(Spec spec);

    /***
     * 删除
     * @param id
     */
    void delete(Integer id);

    /***
     * 多条件搜索
     * @param searchMap
     * @return
     */
    List<Spec> findList(Map<String, Object> searchMap);

    /***
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    Page<Spec> findPage(int page, int size);

    /***
     * 多条件分页查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Spec> findPageByCondition(Map<String, Object> searchMap, int page, int size);

    /**
     * 根据商品分类名称查询规格列表
     *
     * @param categoryName  分类名称
     * @return
     */
    List<Map> findListByCategoryName(String categoryName);
}
