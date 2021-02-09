package pers.store.market.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.product.entity.CategoryEntity;
import pers.store.market.product.vo.CategoryLevel2Vo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> queryByTree();

    void removeBatchByIds(List<Long> asList);

    Long[] findCategoryPath(Long catelogId);

    void updateDetail(CategoryEntity category);

    Map<String, List<CategoryLevel2Vo>> getCategoryJson();

    List<CategoryEntity> getCategoryLevelToOne();
}

