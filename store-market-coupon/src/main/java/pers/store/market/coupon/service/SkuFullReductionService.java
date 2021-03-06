package pers.store.market.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.domain.dto.SkuReductionDto;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:48:22
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveInfo(SkuReductionDto skuReductionDto);
}

