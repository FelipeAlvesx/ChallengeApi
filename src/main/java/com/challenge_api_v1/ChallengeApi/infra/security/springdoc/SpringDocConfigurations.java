package com.challenge_api_v1.ChallengeApi.infra.security.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Challenge Api")
                        .description("A REST API built with Java and Spring Boot, to complete daily challenges")
                        .contact(new Contact()
                                .name("Team Backend")
                                .email("feelipe.devloper@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://challenge-api/v1/license")));
    }

}
