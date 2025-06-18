package com.example.cronjob.Config;


import feign.RequestInterceptor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Configuration
@Slf4j
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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getCredentials() != null) {
                String token = auth.getCredentials().toString();
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }

//    @Bean
//    public RequestInterceptor addHeaderBeforeRequest() {
//        return requestTemplate -> {
//            // Add API Key
//            requestTemplate.header(header, apiKey);
//
//            // Add JWT Token với multiple fallback options
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth != null) {
//                String token = null;
//
//                // Try credentials first
//                if (auth.getCredentials() != null) {
//                    token = auth.getCredentials().toString();
//                }
//                // Try details as fallback
//                else if (auth.getDetails() instanceof WebAuthenticationDetails) {
//                    // Custom logic to extract token from details if needed
//                }
//
//                if (token != null && !token.isEmpty()) {
//                    requestTemplate.header("Authorization", "Bearer " + token);
//                    log.debug("Added JWT token to request");
//                } else {
//                    log.warn("No JWT token found in SecurityContext");
//                }
//            } else {
//                log.warn("No authentication found in SecurityContext");
//            }
//        };
//    }


}
