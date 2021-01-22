package pers.store.market.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:55
 * @description: 订单服务启动类
 */
@SpringBootApplication
@MapperScan("pers.store.market.order.dao")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
