package pers.store.market.product.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Gaofeng
 * @date 2021/2/24 下午6:13
 * @description: 分布式session配置类
 */
@Configuration
@ComponentScan("pers.store.market.session.config")
@EnableRedisHttpSession //开启session
public class SessionConfig {


}
