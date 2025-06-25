package com.xyphx.getwarranty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordConfig {
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
