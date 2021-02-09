package pers.store.market.coupon.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Gaofeng
 * @date 2021/2/7 下午5:57
 * @description: 采购单完成参数vo
 */
@Data
public class PurchaseDoneVo {

    @NotNull
    private Long id;//采购单id
    private List<PurchaseItemDoneVo> items;
}
