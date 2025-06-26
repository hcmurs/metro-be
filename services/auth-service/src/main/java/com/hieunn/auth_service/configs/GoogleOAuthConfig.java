package com.hieunn.auth_service.configs;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
@Component
@Data
@NoArgsConstructor
public class GoogleOAuthConfig {
    private String clientId;

}
