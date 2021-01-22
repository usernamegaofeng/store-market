package pers.store.market.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:18
 * @description: 库存wms服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WareServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareServiceApplication.class, args);
    }
}
