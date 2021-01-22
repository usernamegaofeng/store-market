package pers.store.market.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-22 18:46:29
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

