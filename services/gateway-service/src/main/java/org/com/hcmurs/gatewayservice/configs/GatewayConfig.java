package org.com.hcmurs.gatewayservice.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@ConditionalOnProperty(name = "spring.application.gw-config-version", havingValue = "v2", matchIfMissing = true)
@Configuration
public class GatewayConfig {

    private final String API_PREFIX = "/api";
    private final String STATION_SERVICE = "lb://station-service";

    private final String USER_SERVICE = "lb://user-service";
    private final String AUTH_SERVICE = "lb://auth-service";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("station_service_route", r -> r
                        .path(API_PREFIX + "/stations/**")
//                .filters(f -> f.stripPrefix(1))
                        .uri(STATION_SERVICE))

                .route("user_service_route", r -> r
                        .path(API_PREFIX + "/users/**")
                        .filters(f -> f.rewritePath(
                                "/api/users(?<segment>/?.*)",
                                "/api/v1/users${segment}"
                        ))
                        .uri("http://localhost:4007"))

                .route("auth_service_route", r -> r
                        .path(API_PREFIX + "/auth/**")
                        .filters(f -> f.rewritePath(
                                "/api/auth(?<segment>/?.*)",
                                "/api/v1/auth${segment}"
                        ))
                        .uri("http://localhost:4006"))

                .route("auth_service_route", r -> r
                        .path(API_PREFIX + "/oauth2/authorize/**")
                        .filters(f -> f.rewritePath(
                                "/api/oauth2/authorize(?<segment>/?.*)",
                                "/api/v1/oauth2/authorize${segment}"
                        ))
                        .uri("http://localhost:4006"))
                .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}