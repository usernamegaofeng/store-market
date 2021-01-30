package pers.store.market.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.product.entity.AttrAttrgroupRelationEntity;
import pers.store.market.product.vo.AttrRelationVo;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addRelation(AttrRelationVo[] relationVos);
}

