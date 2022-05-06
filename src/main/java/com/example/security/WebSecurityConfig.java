package com.example.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
    // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 정리
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable() // 기본 로그인 페이지 사용 안함
                // REST API 사용하기 때문에 사용 안함
                .csrf().ignoringAntMatchers("/api/**")
                .and()
                .authorizeRequests() // ↓ 인증 인가 관련 ↓
                    // image 폴더를 login 없이 허용
                    .antMatchers("/images/**").permitAll()
                    // css 폴더를 login 없이 허용
                    .antMatchers("/css/**").permitAll()
                    // 그 외 login 없이 허용하는 범위
                    .antMatchers("/api/register").permitAll()  // 회원가입 페이지 이동
                    .antMatchers("/").permitAll()  // 메인 페이지 이동
                    .antMatchers("/api/login").permitAll() // 로그인
                    .antMatchers("/user/kakao/callback/**").permitAll() // 로그인
                    .antMatchers("/api/logout").permitAll() // 로그아웃
                // 그 외 어떤 요청이든 '인증 필요'
                .anyRequest().authenticated()
                .and()
                    // [로그인 기능]
                    .formLogin().disable();
    }

    // CORS 정책 필터
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
//        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}