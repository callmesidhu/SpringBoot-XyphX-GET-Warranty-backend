package com.xyphx.getwarranty.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI getOpenApi() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("GET-Warranty API")
                                                .version("1.0")
                                                .description("Authentication APIs for user registration"));
        }
}
