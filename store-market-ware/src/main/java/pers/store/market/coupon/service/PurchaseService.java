package pers.store.market.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.coupon.entity.PurchaseEntity;
import pers.store.market.coupon.vo.MergePurchaseVo;
import pers.store.market.coupon.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author Gaofeng
 * @email 944742829@qq.com
 * @date 2021-01-23 16:46:45
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils unReceivePurchase(Map<String, Object> params);

    void mergePurchase(MergePurchaseVo mergePurchaseVo);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

