package com.team4.planit.global.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://treavelrecommend.s3-website-us-east-1.amazonaws.com/")
                .allowedMethods("*")
                .exposedHeaders("Authorization", "RefreshToken","AccessTokenExpireTime")
                .allowCredentials(true)//make client read header("jwt-token")
        ;
    }
}
