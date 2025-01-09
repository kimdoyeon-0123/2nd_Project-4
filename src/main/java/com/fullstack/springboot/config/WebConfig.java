package com.fullstack.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000") // 프론트엔드의 주소를 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
                .allowCredentials(true) // 자격 증명(쿠키 등)을 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .maxAge(3600); // 프리플라이트 응답 캐시 시간을 1시간으로 설정
    }
}
