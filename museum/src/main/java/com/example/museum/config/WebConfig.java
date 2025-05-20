package com.example.museum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问路径 /avatar/** 映射到本地磁盘路径，如 D:/project/uploads/avatar/
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:../uploads/avatar/");
    }
}
