package com.lh.jspj.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author LH
 * @version 1.0
 * @description 解决id精度问题
 * @date 2024/12/19 21:30
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        //全局配置序列化返回JSON处理
        SimpleModule simplemodule = new SimpleModule();
        //JSON Long->String
        simplemodule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simplemodule);
        return objectMapper;
    }
}
