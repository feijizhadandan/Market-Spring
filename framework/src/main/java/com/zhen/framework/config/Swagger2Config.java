package com.zhen.framework.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@EnableKnife4j
// 实现WebMvcConfigurer, 而不是继承WebMvcConfigurationSupport 能够不影响自动配置, 又能自定义重写一些功能(如：自定义消息转换器, 返回对象转换成Json)
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                // 文档信息
                .apiInfo(apiInfo())
                // 扫描的controller包的路径
                .select().apis(RequestHandlerSelectors.basePackage("com.zhen.admin.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .description("Kinfe4j 接口测试文档")
            .contact(new Contact("震", "www.baidu.com", "215628266@qq.com"))
            .version("v1.1.0")
            .title("API测试文档")
            .build();
    }
    
}
