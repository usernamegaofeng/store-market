package pers.store.market.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Gaofeng
 * @date 2021/1/23 下午5:07
 * @description: wms库存服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WareServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareServiceApplication.class, args);
    }
}
