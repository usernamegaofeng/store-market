package pers.store.market.gateway.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author 高枫
 * @date 2021/1/2 下午5:54
 *
 *  自定义跨域配置类
 *      在网关服务中集中配置
 *      两种方式:没有在gateway配置文件中配置了跨域,就需要创建跨域配置类
 *          1.基于配置类
 *          2.基于gateway配置文件
 */
@Configuration
public class CustomCorsConfiguration {

//    @Bean
//    public CorsWebFilter corsWebFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        //1.配置跨域
//        //允许哪种请求头跨域
//        corsConfiguration.addAllowedHeader("*");
//        //允许哪种方法类型跨域 get post delete put
//        corsConfiguration.addAllowedMethod("*");
//        // 允许哪些请求源跨域
//        corsConfiguration.addAllowedOrigin("*");
//        // 是否携带cookie跨域
//        corsConfiguration.setAllowCredentials(true);
//        //允许跨域的路径
//        source.registerCorsConfiguration("/**",corsConfiguration);
//        return new CorsWebFilter(source);
//    }
}
