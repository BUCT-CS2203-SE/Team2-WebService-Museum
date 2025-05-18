package com.example.museum.config;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册拦截器
 */
@Configuration
public class MybatisConfig {

    @Bean
    public Interceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer(Interceptor sqlLogInterceptor) {
        return configuration -> configuration.addInterceptor(sqlLogInterceptor);
    }
}
