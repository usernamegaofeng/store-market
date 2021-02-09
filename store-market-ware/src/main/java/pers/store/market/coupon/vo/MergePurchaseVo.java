package pers.store.market.coupon.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/7 下午4:09
 * @description:
 */
@Data
public class MergePurchaseVo {

    private Long purchaseId;
    private List<Long> items;
}
