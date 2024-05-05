package com.clinic.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Mbarek Skandar",
                        email = "mbarekk.skandar@gmail.com"
                ),
                description = "Welcome to the Clinic Application API documentation! This comprehensive guide will walk you through the endpoints, parameters, authentication methods, and response formats for interacting with the Clinic Application programmatically",
                title = "Clinic Application API Documentation",
                version = "1.0"

        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }


)
@SecurityScheme(
        name = "bearerAuth",
        description = "Insert the token returned from authentication into the value field.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi patientApi() {
        return GroupedOpenApi.builder()
                .group("Patient")
                .pathsToMatch("/api/clinic/patient/**")
                .build();
    }

    @Bean
    public GroupedOpenApi doctorApi() {
        return GroupedOpenApi.builder()
                .group("Doctor")
                .pathsToMatch("/api/clinic/doctor/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("Admin")
                .pathsToMatch("/api/clinic/admin/**")
                .build();
    }



    @Bean
    public GroupedOpenApi visitorApo() {
        return GroupedOpenApi.builder()
                .group("Visitor")
                .pathsToMatch("/api/clinic/auth/**", "/api/clinic/file/**")
                .build();
    }

}
