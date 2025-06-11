package com.hieunn.user_service.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "pocketbase")
public class PocketBaseProperties {
    private String url;
    private Superuser superuser;

    @Data
    public static class Superuser {
        private String email;
        private String password;
    }
}