package pers.store.market.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.store.market.goods.dao.BrandMapper;
import pers.store.market.goods.domain.pojo.Brand;
import pers.store.market.goods.service.IBrandService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/1 下午7:25
 */
@Service
@Log4j2
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 品牌列表查询
     *
     * @return 品牌列表
     */
    @Override
    public List<Brand> findList() {
        return brandMapper.selectAll();
    }

    /**
     * 根据id查询品牌信息
     *
     * @param id 品牌ID
     * @return 品牌实体类
     */
    @Override
    public Brand findById(Integer id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        return brand;
    }

    /**
     * 品牌新增
     *
     * @param brand 品牌实体类
     */
    @Override
    @Transactional
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 品牌修改
     *
     * @param brand 品牌实体类
     */
    @Override
    @Transactional
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 根据id删除品牌信息
     *
     * @param id 品牌ID
     */
    @Override
    @Transactional
    public void delById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 品牌列表条件查询
     *
     * @param searchMap 查询参数
     * @return
     */
    @Override
    public List<Brand> list(Map<String, Object> searchMap) {
        Example example = new Example(Brand.class);
        //封装查询条件
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            //品牌名称(模糊) like  %
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            //按照品牌首字母进行查询(精确)
            if (searchMap.get("letter") != null && !"".equals(searchMap.get("letter"))) {
                criteria.andEqualTo("letter", searchMap.get("letter"));
            }
        }
        List<Brand> brandList = brandMapper.selectByExample(example);
        return brandList;
    }

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 条数
     * @return 数据集
     */
    @Override
    public Page<Brand> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Brand> pageData = (Page<Brand>) brandMapper.selectAll();
        return pageData;
    }

    /**
     * 分页查询带条件
     *
     * @param searchMap 查询参数
     * @param page      页码
     * @param size      条数
     * @return 数据集
     */
    @Override
    public Page<Brand> findPage(Map<String, Object> searchMap, int page, int size) {
        //设置分页
        PageHelper.startPage(page, size);
        //设置查询条件
        Example brandExample = new Example(Brand.class);
        Example.Criteria criteria = brandExample.createCriteria();
        if (searchMap != null) {
            //设置品牌名称模糊查询
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            //设置品牌首字母的精确查询
            if (searchMap.get("letter") != null && !"".equals(searchMap.get("letter"))) {
                criteria.andEqualTo("letter", searchMap.get("letter"));
            }
        }
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(brandExample);
        return pageInfo;
    }
}
