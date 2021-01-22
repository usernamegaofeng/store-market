package pers.store.market.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.ware.entity.SpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:22
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

