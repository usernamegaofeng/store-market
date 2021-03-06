package pers.store.market.order.vo;

import lombok.Data;
import pers.store.market.order.entity.OrderEntity;

/**
 * @author Gaofeng
 * @date 2021/3/5 下午8:42
 * @description:
 */
@Data
public class SubmitOrderResponseVo {

    /**
     * 订单实体类
     */
    private OrderEntity order;

    /**
     * 错误状态码
     **/
    private Integer code;

}
