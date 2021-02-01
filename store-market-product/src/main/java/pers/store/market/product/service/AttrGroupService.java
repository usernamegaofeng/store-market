package pers.store.market.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.product.entity.AttrGroupEntity;
import pers.store.market.product.vo.AttrRelationVo;
import pers.store.market.product.vo.AttrsGroupVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCategoryId(Map<String, Object> params, Long categoryId);

    void removeAttrRelation(AttrRelationVo[] relationVos);

    List<AttrsGroupVo> getAttrGroupWithAttrs(Long categoryId);
}

