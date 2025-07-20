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
    private final String TICKET_SERVICE = "lb://ticket-service";
    private final String ORDER_SERVICE = "lb://order-service";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("station_service_route", r -> r
                        .path(API_PREFIX + "/stations/**", API_PREFIX + "/schedules/**", API_PREFIX + "/routes/**",API_PREFIX +"/station-routes/**",
                              API_PREFIX + "/bus/**")
                        .uri("http://localhost:4004"))

                .route("ticket_service_route", r -> r
                        .path(API_PREFIX + "/ts/**")
                        .uri("http://localhost:4005"))

                .route("order_service_route", r -> r
                        .path(API_PREFIX + "/user/orders/**")
                        .uri("http://localhost:4009"))

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
                        .path(API_PREFIX + "/oauth2/authorization/**")
                        .filters(f -> f.rewritePath(
                                "/api/oauth2/authorization(?<segment>/?.*)",
                                "/api/v1/oauth2/authorization${segment}"
                        ))
                        .uri("http://localhost:4006"))

                .route("notification_service_route", r -> r
                        .path(API_PREFIX + "/notifications/**")
                        .filters(f -> f.rewritePath(
                                "/api/notifications(?<segment>/?.*)",
                                "/api/v1/notifications${segment}"
                        ))
                        .uri("http://localhost:4008"))

                .route("order_service_route", r -> r
                        .path(API_PREFIX + "/orders/**", API_PREFIX + "/payment/**")
                        .uri("http://localhost:4009"))

                .route("cronjob_service_route", r -> r
                        .path(API_PREFIX + "/stat/**")
                        .uri("http://localhost:4010"))
                .build();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}