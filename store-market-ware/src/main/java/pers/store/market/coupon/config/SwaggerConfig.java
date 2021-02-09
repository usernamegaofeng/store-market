package pers.store.market.coupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * @author Gaofeng
 * @date 2021/1/22 下午10:41
 * @description: swagger配置类, 增强版
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Bean(value = "wareApi")
    @Order(value = 1) //执行顺序优先级,数字越小,优先级越高
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("pers.store.market.ware.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("wms库存服务API文档")
                .description("powered by gao")
                .termsOfServiceUrl("localhost:9400")
                .contact(new Contact("Gaofeng", "https://github.com/usernamegaofeng", ""))
                .version("1.0")
                .build();
    }

}
