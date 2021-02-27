package pers.store.market.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Gaofeng
 * @date 2021/2/16 下午9:25
 * @description: 线程池参数配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "thread.config")
public class ThreadConfigProperties {

    private Integer coreSize;      //最大核心
    private Integer maxSize;       //最大线程数量
    private Integer keepAliveTime; //空闲线程存活时间
}
