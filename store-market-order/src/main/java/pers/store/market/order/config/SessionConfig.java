package pers.store.market.order.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author Gaofeng
 * @date 2021/2/24 下午6:13
 * @description: 分布式session配置类
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig {

    /**
     * 自定义cookie
     *
     * @return cookieSerializer
     */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("STORESESSION");  //cookie名称
        defaultCookieSerializer.setDomainName("localhost"); //cookie作用域
        return defaultCookieSerializer;
    }

    /**
     * 自定义数据序列化器,默认使用的是jdk的Serializer
     *
     * @return redisSerializer
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }
}
