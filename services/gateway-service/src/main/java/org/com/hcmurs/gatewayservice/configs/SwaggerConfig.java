package org.com.hcmurs.gatewayservice.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Gateway Services",
        version = "1.0.0",
        description = "Gateway API documentation",
        termsOfService = "Terms and conditions applied",
        contact = @Contact(
            name = "Hoang Cao Luu",
            email = "team@gmail.com",
            url = "https://team.example.com"
        ),
        license = @License(name = "Hoang License")
    ),
    servers = {
        @Server(
            description = "Development Server",
            url = "http://localhost:4003"
        ),
        @Server(
            description = "Test Server",
            url = "http://localhost:4003"
        )
    },
    security = {@SecurityRequirement(name = "bearer-key")}
)
@SecurityScheme(
    name = "bearer-key",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    description = "JWT Bearer authentication",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

    public static final String BEARER_KEY_SECURITY_SCHEME = "bearer-key";

}