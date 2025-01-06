package com.fullstack.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()  // CSRF 보호를 비활성화 (API 요청에 대해 필요할 수 있음)
            .authorizeRequests()
            .antMatchers("/order/payment").permitAll()  // 결제 API만 허용
            .anyRequest().authenticated();  // 다른 요청은 인증 필요
    }
}
