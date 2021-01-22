package pers.store.market.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

