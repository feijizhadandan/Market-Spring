package com.zhen.framework.config;

import com.zhen.framework.common.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SpringMVC 配置类
 * WebMvcConfigurer 能在不影响自动装配的情况下, 进行自定义配置
 * WebMvcConfigurationSupport 会使自动配置失效(不推荐)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // knife4j 静态资源映射
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 积木报表 静态资源映射
        registry.addResourceHandler("/jmreport/desreport_/**").addResourceLocations("classpath:/static/jmreport/desreport_/");
    }

    /**
     * 添加自定义的序列化器, 用于序列化 LocalDateTime 和 Long类型
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //消息转换器,将service返回的数据转换成json
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //追加到转换器容器中，index为0表示优先级最高
        converters.add(0, messageConverter);
    }
}
