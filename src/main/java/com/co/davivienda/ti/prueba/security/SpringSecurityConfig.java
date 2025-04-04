package com.co.davivienda.ti.prueba.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/davi-events/users/register").permitAll()
                        .requestMatchers("/api/davi-events/users/login").permitAll()
                        .requestMatchers("/api/davi-events/events/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(config -> config.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .build();
    }
}