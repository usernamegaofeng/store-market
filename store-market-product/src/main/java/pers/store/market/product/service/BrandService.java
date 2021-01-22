package pers.store.market.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:00:45
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

