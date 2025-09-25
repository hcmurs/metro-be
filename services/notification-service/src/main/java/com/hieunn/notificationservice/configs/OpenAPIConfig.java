/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.time.LocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    final String apiKeyName = "X-API-KEY";

    return new OpenAPI()
        .info(
            new Info()
                .title("Notification Service API")
                .version("1.0")
                .description("API Documentation for Notification Service")
                .contact(new Contact().name("Notification Service Team")))
        .externalDocs(
            new ExternalDocumentation()
                .description("Additional Documentation")
                .url("https://example.com"))
        .components(
            new Components()
                .addSecuritySchemes(
                    apiKeyName,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name(apiKeyName))
                .addSchemas(
                    "LocalTime",
                    new Schema<LocalTime>().type("string").pattern("HH:mm").example("00:00")))
        .addSecurityItem(new SecurityRequirement().addList(apiKeyName))
        .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
  }
}
