package pers.store.market.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:50
 * @description: 优惠服务启动类
 */
@SpringBootApplication
@MapperScan("pers.store.market.coupon.dao")
public class CouponServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponServiceApplication.class, args);
    }
}
