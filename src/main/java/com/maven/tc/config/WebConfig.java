package com.maven.tc.config;

import com.maven.tc.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/admin/**", "/api/student/**")
                .excludePathPatterns(
                        "/api/student/login",
                        "/api/student/register",
                        "/api/student/directions",
                        "/api/student/learningPaths",
                        "/api/admin/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }
}