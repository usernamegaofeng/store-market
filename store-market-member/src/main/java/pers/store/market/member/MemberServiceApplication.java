package pers.store.market.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Gaofeng
 * @date 2021/1/22 下午8:52
 * @description: 会员服务启动类
 */
@SpringBootApplication
@MapperScan("pers.store.market.member.dao")
public class MemberServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }
}
