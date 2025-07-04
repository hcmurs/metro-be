package com.hieunn.user_service.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String apiKeyName = "X-API-KEY";

        return new OpenAPI()
            .info(new Info()
                      .title("User Service API")
                      .version("1.0")
                      .description("API Documentation for User Service")
                      .contact(new Contact().name("User Service Team")))
            .externalDocs(new ExternalDocumentation()
                              .description("Additional Documentation")
                              .url("https://example.com"))
            .components(new Components()
                            .addSecuritySchemes(apiKeyName,
                                                new SecurityScheme()
                                                    .type(SecurityScheme.Type.APIKEY)
                                                    .in(SecurityScheme.In.HEADER)
                                                    .name(apiKeyName))



                            .addSchemas("LocalTime", new Schema<LocalTime>()
                                .type("string")
                                .pattern("HH:mm")
                                .example("00:00")))
            .addSecurityItem(new SecurityRequirement().addList(apiKeyName))
            .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}
