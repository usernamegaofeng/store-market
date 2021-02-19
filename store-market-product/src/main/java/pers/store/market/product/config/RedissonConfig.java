package pers.store.market.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Gaofeng
 * @date 2021/2/12 上午10:00
 * @description: redisson配置类
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
//    @Value("${spring.redis.password}")
//    private String password;

    /**
     * 所有对Redisson的使用都是通过RedissonClient
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        //1、创建配置,redis://开头 or 带有ssl链接使用rediss://
        String address = "redis://" + host + ":" + port;
        Config config = new Config();
        config.useSingleServer().setAddress(address);
        //config.useSingleServer().setPassword(password);
        //2、根据Config创建出RedissonClient实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
