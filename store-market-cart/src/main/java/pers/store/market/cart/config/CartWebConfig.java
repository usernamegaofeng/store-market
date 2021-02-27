package pers.store.market.cart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.store.market.cart.interceptor.CartInterceptor;

/**
 * @author Gaofeng
 * @date 2021/2/25 下午5:58
 * @description: web配置类
 */
@Configuration
public class CartWebConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CartInterceptor()).addPathPatterns("/**");  //任何请求
    }
}
