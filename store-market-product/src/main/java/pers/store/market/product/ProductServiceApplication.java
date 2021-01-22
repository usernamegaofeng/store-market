package pers.store.market.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:57
 * @description: 商品服务启动类
 */
@SpringBootApplication
@MapperScan("pers.store.market.product.dao")
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
