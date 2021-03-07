package pers.store.market.ware.listener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.store.market.common.constant.RabbitmqConstant;
import pers.store.market.common.domain.dto.mq.StockLockedDto;
import pers.store.market.ware.service.WareSkuService;

import java.io.IOException;

/**
 * @author Gaofeng
 * @date 2021/3/6 下午10:53
 * @description: 库存释放Listener
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitmqConstant.STOCK_RELEASE_STOCK_QUEUE)
public class StockReleaseListener {

    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 释放订单锁定的库存
     */
    @RabbitHandler
    public void handleStockLockedRelease(StockLockedDto stockLockedDto, Message message, Channel channel) throws IOException {
        log.info("释放订单队列收到需要释放的订单库存数据 ====> {}", stockLockedDto.toString());
        try {
            wareSkuService.releaseStock(stockLockedDto);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("释放订单队列出现异常 ====> {}", e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
