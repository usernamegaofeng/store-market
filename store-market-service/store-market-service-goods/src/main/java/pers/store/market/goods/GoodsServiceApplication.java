package pers.store.market.goods;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pers.store.market.common.utils.IdWorker;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 高枫
 * @date 2021/1/1 下午6:58
 * <p>
 * 品牌服务启动器
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableSwagger2Doc
@MapperScan("pers.store.market.goods.dao")
public class GoodsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsServiceApplication.class, args);
    }

    @Value("${workerId}")
    private Integer workerId;
    @Value("${datacenterId}")
    private Integer datacenterId;

    @Bean
    public IdWorker idWorker() {
        IdWorker idWorker = new IdWorker(workerId, datacenterId);
        return idWorker;
    }
}
