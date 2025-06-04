package org.com.hcmurs.gatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@ConditionalOnProperty(name = "spring.application.gw-config-version", havingValue = "v2", matchIfMissing = true)
@Configuration
public class GatewayConfig {

    private final String API_PREFIX = "/api";
    private final String STATION_SERVICE = "lb://station-service";

    private final String USER_SERVICE = "lb://user-service";
    @Value("${service.users.version}")
    private String USER_SERVICE_VERSION;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("station_service_route", r -> r
                        .path(API_PREFIX + "/stations/**")
//                .filters(f -> f.stripPrefix(1))
                        .uri(STATION_SERVICE))

                .route("user_service_route", r -> r
                        .path(API_PREFIX + "/v1/users/**")
                        .uri(USER_SERVICE))
                .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}