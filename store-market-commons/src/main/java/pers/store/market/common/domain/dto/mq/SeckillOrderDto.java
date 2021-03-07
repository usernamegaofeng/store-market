package pers.store.market.common.domain.dto.mq;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Gaofeng
 * @date 2021/3/6 下午10:33
 * @description: 秒杀订单dto
 */
@Data
public class SeckillOrderDto {

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 会员ID
     */
    private Long memberId;
}
