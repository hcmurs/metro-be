package org.com.hcmurs.gatewayservice.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.com.hcmurs.gatewayservice.dtos.ApiResponse;
import org.com.hcmurs.gatewayservice.utils.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAuthenticationFilter implements WebFilter {
    final JwtUtil jwtUtil;
    final RedisTemplate<String, String> redisTemplate;
    final ObjectMapper objectMapper;
    final PathPatternParser patternParser;

    @Value("${security.api.header}")
    String apiHeader;

    @Value("${security.api.key}")
    String apiKey;

    final List<String> PUBLIC_ENDPOINTS = List.of(
            //Swagger
            "/swagger-ui.html",
            "/swagger-ui/**",  // Add this line for newer Swagger UI paths
            "/favicon.ico",
            "/v3/api-docs/**",
            "/webjars/swagger-ui/**",
            //Auth
            "/api/oauth2/authorization/**",
            "/api/auth/local-login",
            "/api/auth/**",
            "/api/v1/auth/**",
            //User
            "/api/users/blogs/**",
            "/api/users/is-username-exist",
            "/api/users/is-email-exist",
            "/api/users/register",
            "/api/users/reset-password",
            "/api/users/requests/**",
            "/api/users/feedbacks/**",
            //Notification
            "/api/notifications/send-otp",
            "/api/notifications/verify-otp",
            "/actuator/health",
            //Stations
            "/api/routes",
            "/api/routes/{id}",
            "/api/routes/search",
            "/api/routes/code/{routeCode}",
            "/api/stations",
            "/api/stations/{id}",
            "/api/stations/search",
            "/api/stations/route/{routeId}",
            "/api/schedules",
            "/api/schedules/{id}",
            "/api/schedules/station/{stationId}",
            "/api/bus/**",
            //ticket
//            "/api/ts/**",
            "/api/ts/tickets/scan/entry",
            "/api/ts/tickets/scan/exit",
            "/api/orders/**",
            "/api/ts/fare-matrices/{id}",
            "/api/ts/fare-matrices",
            "/api/ts/fare-matrices/by-station/{stationId}",
            "/api/ts/fare-matrices/get-fare",
            "/api/ts/ticket-types/{id}",
            "/api/ts/ticket-types",
            "/api/ts/tickets/generate-qr",
            "api/payment/callback/**",
            "/aggregate/**",
            "/api/orders/payment-methods/get-all",
            "/api/ts/tickets/qr",
            "/api/payment/stripe/**",
            "/api/ts/ticket-usage-logs/between",
            "/api/station-routes/route/{routeId}",
            "/api/station-routes/{id}"
            );

    @NotNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        HttpCookie jwtCookie = exchange.getRequest().getCookies().getFirst("token");
        String token = jwtCookie != null ? jwtCookie.getValue() : null;

        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate()
                .header(apiHeader, apiKey);

        if (token != null) {
            requestBuilder.header("Authorization", "Bearer " + token);
        }

        ServerHttpRequest modifiedRequest = requestBuilder.build();
        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(modifiedRequest)
                .build();

        if (isPublicPath(path)) {
            return chain.filter(modifiedExchange);
        }

        if (token == null) {
            return unauthorized(exchange);
        }

        try {
            String jti = jwtUtil.extractJti(token);
            Boolean isRevoked = redisTemplate.hasKey(jti);
            if (Boolean.TRUE.equals(isRevoked)) {
                return unauthorized(exchange);
            }

            return chain.filter(modifiedExchange);
        } catch (Exception ignored) {
            return unauthorized(exchange);
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        exchange.getResponse()
                .addCookie(ResponseCookie.from("token", "")
                        .path("/")
                        .httpOnly(true)
                        .secure(true)
                        .maxAge(0)
                        .build());
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(ApiResponse.error(401, "Unauthorized"));
            return exchange.getResponse()
                    .writeWith(Mono.just(exchange.getResponse()
                            .bufferFactory()
                            .wrap(bytes)));
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isPublicPath(String path) {
        PathContainer pathContainer = PathContainer.parsePath(path);
        return PUBLIC_ENDPOINTS.stream()
                .map(patternParser::parse)
                .anyMatch(pattern -> pattern.matches(pathContainer));
    }
}
