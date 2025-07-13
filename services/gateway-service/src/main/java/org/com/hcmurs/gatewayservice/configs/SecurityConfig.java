package org.com.hcmurs.gatewayservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final String[] freeResources = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/aggregate/**",
            "/actuator/**",
            "/webjars/**",
            "/api/v1/auth/**",
            "/api/auth/**",
            "/api/ts/fare-matrices/{id}",
            "/api/ts/fare-matrices",
            "/api/ts/fare-matrices/by-station/{stationId}",
            "/api/ts/fare-matrices/get-fare",
            "/api/ts/ticket-types/{id}",
            "/api/ts/ticket-types",
            "/api/routes",
            "/api/routes/{id}",
            "/api/routes/search",
            "/api/routes/code/{routeCode}",
            "/api/stations",
            "/api/stations/{id}",
            "/api/stations/search",
            "api/stations/route/{routeId}",
            "/api/schedules",
            "/api/ts/tickets/scan/entry",
            "/api/ts/tickets/scan/exit",
            "/api/schedules/{id}",
            "/api/schedules/station/{stationId}",
            "/api/orders/payment-methods/get-all",
            "/api/ts/tickets/generate-qr",
            "/api/ts/tickets/qr",
            "/api/bus/**",

            // User Service
            "/api/users/blogs/**",
            "/api/users/requests/**",
            "/api/users/feedbacks/**",

            "/api/payment/stripe/**"
    };

    @Bean
    public SecurityWebFilterChain securityFilterChain (ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(Customizer.withDefaults())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(freeResources).permitAll()
                        .anyExchange().permitAll())
                .build();
    }
}
