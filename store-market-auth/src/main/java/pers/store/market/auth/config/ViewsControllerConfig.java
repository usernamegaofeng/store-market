package pers.store.market.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Gaofeng
 * @date 2021/2/17 下午3:08
 * @description: 视图页面跳转配置
 * 不需要写controller进行页面的跳转逻辑
 */
@Configuration
public class ViewsControllerConfig implements WebMvcConfigurer {

    /**
     * 配置路径和页面映射
     *
     * @param registry 视图注册
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/register.html").setViewName("register");
    }
}
