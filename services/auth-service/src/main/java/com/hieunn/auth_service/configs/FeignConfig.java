package com.hieunn.auth_service.configs;


import feign.RequestInterceptor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeignConfig {
    @Value("${security.api.header}")
    String header;

    @Value("${security.api.key}")
    String apiKey;

    @Bean
    public RequestInterceptor addHeaderBeforeRequest() {
        return requestTemplate -> {
            requestTemplate.header(header, apiKey);
        };
    }
}
