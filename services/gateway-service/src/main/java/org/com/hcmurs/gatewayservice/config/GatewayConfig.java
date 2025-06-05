package org.com.hcmurs.gatewayservice.config;

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
     private final String TICKET_SERVICE = "lb://ticket-service";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("station_service_route", r -> r
                .path(API_PREFIX + "/stations/**")
//                .filters(f -> f.stripPrefix(1))
                .uri(STATION_SERVICE))
            .route("ticket_service_route", r -> r
                .path(API_PREFIX + "/minh/**")
                .uri(TICKET_SERVICE))
            .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}