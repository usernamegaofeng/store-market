package pers.store.market.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;
import pers.store.market.product.dao.CategoryDao;
import pers.store.market.product.entity.CategoryEntity;
import pers.store.market.product.service.CategoryService;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询所有分类,使用递归查询所有的分类以及其子分类
     * 1.先查出所有的一级分类
     * 2.根据一级分类的ID去查找二级分类的parentId
     * 3.递归查询,一级一级往下查,直到没有下一级的分类
     *
     * @return List<CategoryEntity>
     */
    @Override
    public List<CategoryEntity> queryByTree() {
        List<CategoryEntity> categoryEntityList = this.baseMapper.selectList(null);
        //找出所有的一级分类以及其子类
        return categoryEntityList.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map(category -> {
                    category.setChildrenList(getChildrenList(category, categoryEntityList));
                    return category;
                })
                .sorted((beforeEntity, afterEntity) -> {
                    return (beforeEntity.getSort() == null ? 0 : beforeEntity.getSort()) - (afterEntity.getSort() == null ? 0 : afterEntity.getSort()); //进行排序,将前面实体的排序减去后面实体的排序
                }).collect(Collectors.toList());
    }

    //使用递归查询所有的分类以及其子分类
    private List<CategoryEntity> getChildrenList(CategoryEntity category, List<CategoryEntity> categoryEntityList) {
        List<CategoryEntity> childrenList = categoryEntityList.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == category.getCatId())
                .map(categoryEntity -> {
                    categoryEntity.setChildrenList(getChildrenList(categoryEntity, categoryEntityList));
                    return categoryEntity;
                })
                .sorted(Comparator.comparingInt(beforeEntity -> (beforeEntity.getSort() == null ? 0 : beforeEntity.getSort())))
                .collect(Collectors.toList());
        return childrenList;
    }


    /**
     * 批量删除(逻辑删除)
     *
     * @param ids 分类ID数组
     *            todo 如果分类有其他关联,不能进行删除操作
     */
    @Override
    public void removeBatchByIds(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
    }
}