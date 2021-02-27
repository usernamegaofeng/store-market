package pers.store.market.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.store.market.order.interceptor.OrderLoginInterceptor;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:58
 * @description: web配置类
 */
@Configuration
public class OrderConfiguration implements WebMvcConfigurer {

    @Autowired
    private OrderLoginInterceptor orderLoginInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderLoginInterceptor).addPathPatterns("/**");  //任何请求
    }
}
