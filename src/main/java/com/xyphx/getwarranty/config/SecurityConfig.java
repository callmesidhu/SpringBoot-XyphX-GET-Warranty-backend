package com.xyphx.getwarranty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf().disable() // disables CSRF (needed for form-based apps)
                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().permitAll() // allow everything (for dev)
                                )
                                .httpBasic().disable() // disable HTTP Basic Auth
                                .formLogin().disable(); // disable form login

                return http.build();
        }
}
