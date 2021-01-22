package pers.store.market.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:52
 * @description: 会员服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MemberServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }
}
