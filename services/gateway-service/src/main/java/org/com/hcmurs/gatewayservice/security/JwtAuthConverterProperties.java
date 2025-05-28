package org.com.hcmurs.gatewayservice.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
@Getter
@Setter
public class JwtAuthConverterProperties {

    @NotBlank
    private String resourceId;
    private String principalAttribute;

}