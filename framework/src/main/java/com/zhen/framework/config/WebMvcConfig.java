package com.zhen.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 积木报表的静态资源映射
        registry.addResourceHandler("/jmreport/desreport_/**").addResourceLocations("classpath:/static/jmreport/desreport_/");
    }
}
