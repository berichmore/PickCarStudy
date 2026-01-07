package com.erp.global.config;

import com.erp.global.jwt.JwtAuthenticationFilter;
import com.erp.global.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/api/client/login", "/api/client/register").permitAll()
                                .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)

                .sessionManagement(
                        session
                                -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                )
                .build();
    }

    // AuthenticationManager Bean 등록
    // -> Spring이 알아서 UserDetailsService와 PasswordEncoder를 가져다 씀
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception{
        return configuration.getAuthenticationManager();


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호를 안전하게 암호화해주는 기계 (BCrypt 알고리즘  사용)
        return new BCryptPasswordEncoder();
    }
}
