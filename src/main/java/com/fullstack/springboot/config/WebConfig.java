package com.fullstack.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // React 앱의 URL에서 API를 호출할 수 있도록 CORS 설정
        registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000") // React 앱의 URL
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true); // 쿠키 또는 인증 정보를 포함하여 요청을 허용
    }
}
