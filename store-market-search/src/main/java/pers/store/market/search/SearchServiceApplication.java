package pers.store.market.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Gaofeng
 * @date 2021/2/8 下午11:22
 * @description: 搜索服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SearchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }
}
