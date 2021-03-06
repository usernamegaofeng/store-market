package pers.store.market.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午9:15
 * @description: 运费vo
 */
@Data
public class FareVo {
    private MemberAddressVo address;
    private BigDecimal fare;
}
