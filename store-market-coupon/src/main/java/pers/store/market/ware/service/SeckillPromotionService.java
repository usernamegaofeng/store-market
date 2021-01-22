package pers.store.market.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.ware.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:23
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

