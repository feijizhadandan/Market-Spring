package com.zhen.framework.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson返回前端数据时，自定义的序列化器，用于序列化LocalDateTime
 * @Bean 会使序列化器和反序列化器自动注入到ObjectMapper中
 * 
 * 但是现在直接在JacksonObjectMapper中进行统一管理了
 */

//现在直接在JacksonObjectMapper中进行统一序列化方式管理了, 该组件已弃用

//@Configuration
//public class LocalDateTimeSerializerConfig {
//    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
//    private String pattern;
//
//    // localDateTime 序列化器
//    @Bean
//    public LocalDateTimeSerializer localDateTimeSerializer() {
//        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern));
//    }
//    // localDateTime 反序列化器
//    @Bean
//    public LocalDateTimeDeserializer localDateTimeDeserializer() {
//        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern));
//    }
//
//    //@Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        //return new Jackson2ObjectMapperBuilderCustomizer() {
//        //    @Override
//        //    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
//        //        jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        //        jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
//        //        jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class,localDateTimeDeserializer());
//        //    }
//        //};
//        //这种方式同上
//        return builder -> {
//            builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
//            builder.deserializerByType(LocalDateTime.class,localDateTimeDeserializer());
//            builder.simpleDateFormat(pattern);
//        };
//    }
//
//}
