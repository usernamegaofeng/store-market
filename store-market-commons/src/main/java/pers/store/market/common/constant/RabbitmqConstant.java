package pers.store.market.common.constant;

/**
 * @author Gaofeng
 * @date 2021/3/6 下午10:38
 * @description: rabbitmq常量类
 */
public class RabbitmqConstant {

    /**
     * 库存服务
     */
    public static final String STOCK_EVENT_EXCHANGE = "stock-event-exchange";   //交换机
    public static final String STOCK_DELAY_QUEUE = "stock.delay.queue";         //死信队列
    public static final String STOCK_RELEASE_STOCK_QUEUE = "stock.release.stock.queue"; //解锁库存队列
    public static final String STOCK_LOCK_ROUTING_KEY = "stock.locked";                 //锁定库存路由键
    public static final String STOCK_RELEASE_ROUTING_KEY = "stock.release.#";           //释放库存路由键
}
