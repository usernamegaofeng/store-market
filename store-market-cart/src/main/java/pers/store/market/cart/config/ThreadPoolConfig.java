package pers.store.market.cart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午9:21
 * @description: 线程池配置类
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadConfigProperties threadConfigProperties) {
        return new ThreadPoolExecutor(threadConfigProperties.getCoreSize(),
                threadConfigProperties.getMaxSize(), threadConfigProperties.getKeepAliveTime(), TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());  //拒绝策略,将最新的任务丢弃
    }
}
