package com.fullstack.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().ignoringAntMatchers("/order/**") // CSRF 보호 해제
            .and()
            .authorizeRequests()
            .antMatchers("/**").permitAll() // 결제 엔드포인트에 대해 접근 허용
            .anyRequest().authenticated(); // 다른 모든 요청은 인증 필요
    }
}
