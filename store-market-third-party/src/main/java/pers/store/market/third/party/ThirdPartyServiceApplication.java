package pers.store.market.third.party;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Gaofeng
 * @date 2021/1/25 下午5:53
 * @description: 第三方组件服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ThirdPartyServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThirdPartyServiceApplication.class, args);
    }
}
