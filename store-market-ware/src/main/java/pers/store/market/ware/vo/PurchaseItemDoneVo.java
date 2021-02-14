package pers.store.market.ware.vo;

import lombok.Data;

/**
 * @author Gaofeng
 * @date 2021/2/7 下午5:57
 * @description: 采购项参数vo
 */
@Data
public class PurchaseItemDoneVo {

    private Long itemId;
    private Integer status;
    private String reason;
}
