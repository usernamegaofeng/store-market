package pers.store.market.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.domain.dto.OrderDto;
import pers.store.market.common.domain.dto.mq.StockLockedDto;
import pers.store.market.common.domain.vo.SkuHasStockVo;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.ware.entity.WareSkuEntity;
import pers.store.market.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:46
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> hasStock(List<Long> skuIds);

    boolean orderLockStock(WareSkuLockVo vo);

    void releaseStock(StockLockedDto stockLockedDto);

    void releaseStock(OrderDto orderDto);
}

