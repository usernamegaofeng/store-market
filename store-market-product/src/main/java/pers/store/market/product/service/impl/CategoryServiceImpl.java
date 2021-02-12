package pers.store.market.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;
import pers.store.market.product.dao.CategoryDao;
import pers.store.market.product.entity.CategoryEntity;
import pers.store.market.product.service.CategoryBrandRelationService;
import pers.store.market.product.service.CategoryService;
import pers.store.market.product.vo.CategoryLevel2Vo;
import springfox.documentation.spring.web.json.Json;

@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

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

    /**
     * 查询分类完整路径
     *
     * @param categoryId 分类ID
     * @return
     */
    @Override
    public Long[] findCategoryPath(Long categoryId) {
        List<Long> pathList = new ArrayList<>();
        List<Long> parentPath = findParentPath(categoryId, pathList);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }


    //递归查询所有的分类ID,只要不是0的一级分类
    private List<Long> findParentPath(Long categoryId, List<Long> path) {
        //将当前ID添加到集合中去
        path.add(categoryId);
        CategoryEntity categoryEntity = this.getById(categoryId);
        if (categoryEntity.getParentCid() != 0) {
            findParentPath(categoryEntity.getParentCid(), path);
        }
        return path;
    }

    /**
     * 修改操作,冗余字段数据保持一致
     *
     * @param category 分类实体
     */
    @Override
    @Transactional
    public void updateDetail(CategoryEntity category) {
        this.updateById(category);
        if (StringUtils.isNotBlank(category.getName())) {
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    /**
     * 获取所有一级分类
     *
     * @return List<CategoryEntity>
     */
    @Override
    public List<CategoryEntity> getCategoryLevelToOne() {
        return this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    /**
     * 获取分类首页分类列表
     *
     * @return Map<String, List < CategoryLevel2Vo>>
     */
    @Override
    public Map<String, List<CategoryLevel2Vo>> getCategoryJson() {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("categoryJson-lock");
        //使用读锁
        RLock rLock = readWriteLock.readLock();
        Map<String, List<CategoryLevel2Vo>> dataMap = null;
        try {
            rLock.lock();
            String categoryJson = stringRedisTemplate.opsForValue().get("categoryJson");
            if (StringUtils.isBlank(categoryJson)) {
                System.out.println("查询了缓存............");
                //查询所有一级分类
                List<CategoryEntity> categoryOneList = getCategoryLevelToOne();
                //查询二级分类
                dataMap = categoryOneList.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                    //查询一级分类下的二级分类
                    List<CategoryEntity> categoryTwoList = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
                    List<CategoryLevel2Vo> categoryLevel2VoList = null;
                    if (categoryTwoList != null) {
                        categoryLevel2VoList = categoryTwoList.stream().map(two -> {
                            //封装数据
                            CategoryLevel2Vo categoryLevel2Vo = new CategoryLevel2Vo(two.getCatId().toString(), two.getName(), v.getCatId().toString(), null);
                            //三级分类列表
                            List<CategoryEntity> categoryThreeList = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", two.getCatId()));
                            List<CategoryLevel2Vo.CategoryLevel3Vo> collect = null;
                            if (categoryThreeList != null) {
                                collect = categoryThreeList.stream().map(three -> {
                                    CategoryLevel2Vo.CategoryLevel3Vo categoryLevel3Vo = new CategoryLevel2Vo.CategoryLevel3Vo();
                                    categoryLevel3Vo.setId(three.getCatId().toString());
                                    categoryLevel3Vo.setName(three.getName());
                                    categoryLevel3Vo.setCatalog2Id(two.getCatId().toString());
                                    return categoryLevel3Vo;
                                }).collect(Collectors.toList());
                            }
                            categoryLevel2Vo.setCatalog3List(collect);
                            return categoryLevel2Vo;
                        }).collect(Collectors.toList());
                    }
                    return categoryLevel2VoList;
                }));
                stringRedisTemplate.opsForValue().set("categoryJson", JSON.toJSONString(dataMap));
                return dataMap;
            }
            Map<String, List<CategoryLevel2Vo>> result = JSON.parseObject(categoryJson, new TypeReference<Map<String, List<CategoryLevel2Vo>>>() {
            });
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return null;
    }

}