package com.chat.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
            .info(
                new Info()
                .title("chat API")
                .description("teste para nginx")
                .version("1.0.0")
                .contact(
                    new Contact()
                    .email("jonas.lara@avmb.com.br")
                    .name("jonas")
                )
            )
            .components(
                new Components()
                .addSecuritySchemes(
                    "bearerAuth",
                    new SecurityScheme()
                    .name("bearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                )
            );
    }
}
