package pers.store.market.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.store.market.goods.dao.SpecMapper;
import pers.store.market.goods.domain.pojo.Spec;
import pers.store.market.goods.service.ISpecService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/2 下午5:04
 */
@Service
@Log4j2
public class SpecServiceImpl implements ISpecService {

    @Autowired
    private SpecMapper specMapper;

    /**
     * 查询全部列表
     *
     * @return
     */
    @Override
    public List<Spec> findAll() {
        return specMapper.selectAll();
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public Spec findById(Integer id) {
        return specMapper.selectByPrimaryKey(id);
    }


    /**
     * 增加
     *
     * @param spec
     */
    @Override
    public void add(Spec spec) {
        specMapper.insert(spec);
    }


    /**
     * 修改
     *
     * @param spec
     */
    @Override
    public void update(Spec spec) {
        specMapper.updateByPrimaryKey(spec);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        specMapper.deleteByPrimaryKey(id);
    }


    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @Override
    public List<Spec> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return specMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Spec> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        return (Page<Spec>) specMapper.selectAll();
    }

    /**
     * 条件加分页查询
     *
     * @param searchMap 查询条件
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @Override
    public Page<Spec> findPageByCondition(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        return (Page<Spec>) specMapper.selectByExample(example);
    }

    /**
     * 根据分类名称查询规格列表
     *
     * @param categoryName 分类名称
     * @return 规格列表
     */
    @Override
    public List<Map> findListByCategoryName(String categoryName) {
        List<Map> specList = specMapper.findListByCategoryName(categoryName);
        specList.forEach(map -> map.put("options", ((String) map.get("options")).split(",")));
        return specList;
    }


    /**
     * 构建查询对象
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Spec.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 规格选项
            if (searchMap.get("options") != null && !"".equals(searchMap.get("options"))) {
                criteria.andLike("options", "%" + searchMap.get("options") + "%");
            }

            // ID
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 排序
            if (searchMap.get("seq") != null) {
                criteria.andEqualTo("seq", searchMap.get("seq"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }

        }
        return example;
    }
}
