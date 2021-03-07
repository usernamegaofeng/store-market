package pers.store.market.ware.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.store.market.common.constant.RabbitmqConstant;

import java.util.HashMap;

/**
 * @author Gaofeng
 * @date 2021/3/6 下午10:28
 * @description: rabbitmq配置类
 */
@EnableRabbit
@Configuration
public class RabbitmqConfig {

    @Bean
    public MessageConverter messageConverter() {
        //在容器中导入Json的消息转换器
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange stockEventExchange() {
        return new TopicExchange(RabbitmqConstant.STOCK_EVENT_EXCHANGE, true, false);
    }

//    @RabbitListener(queues = RabbitmqConstant.STOCK_RELEASE_STOCK_QUEUE)
//    public void init(){
//
//    }

    /**
     * 延迟队列(死信队列)
     *
     * @return
     */
    @Bean
    public Queue stockDelayQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "stock-event-exchange");
        arguments.put("x-dead-letter-routing-key", "stock.release");
        // 消息过期时间 2分钟
        arguments.put("x-message-ttl", 120000);
        return new Queue(RabbitmqConstant.STOCK_DELAY_QUEUE, true, false, false, arguments);
    }

    /**
     * 普通队列，用于解锁库存
     *
     * @return
     */
    @Bean
    public Queue stockReleaseStockQueue() {
        return new Queue(RabbitmqConstant.STOCK_RELEASE_STOCK_QUEUE, true, false, false, null);
    }


    /**
     * 交换机和延迟队列绑定
     *
     * @return
     */
    @Bean
    public Binding stockLockedBinding() {
        return new Binding(RabbitmqConstant.STOCK_DELAY_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitmqConstant.STOCK_EVENT_EXCHANGE,
                RabbitmqConstant.STOCK_LOCK_ROUTING_KEY,
                null);
    }

    /**
     * 交换机和普通队列绑定
     *
     * @return
     */
    @Bean
    public Binding stockReleaseBinding() {
        return new Binding(RabbitmqConstant.STOCK_RELEASE_STOCK_QUEUE,
                Binding.DestinationType.QUEUE,
                RabbitmqConstant.STOCK_EVENT_EXCHANGE,
                RabbitmqConstant.STOCK_RELEASE_ROUTING_KEY,
                null);
    }
}
