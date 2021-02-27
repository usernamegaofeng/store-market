package pers.store.market.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Gaofeng
 * @date 2021/2/27 下午9:47
 * @description: feign拦截器功能
 * feign远程调用请求头参数消失
 */
@Configuration
public class FeignInterceptorConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                //RequestContextHolder是spring提供的上下文请求对象,在同一个线程下获取请求数据
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    HttpServletRequest request = requestAttributes.getRequest();
                    String cookie = request.getHeader("Cookie");
                    //设置cookie,防止feign远程调用请求头参数消失的问题
                    requestTemplate.header("Cookie", cookie);
                }
            }
        };

    }
}
