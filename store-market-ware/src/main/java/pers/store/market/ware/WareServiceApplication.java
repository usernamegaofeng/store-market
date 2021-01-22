package pers.store.market.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:18
 * @description: 库存服务启动类
 */
@SpringBootApplication
@MapperScan("pers.store.market.ware.dao")
public class WareServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WareServiceApplication.class,args);
    }
}
