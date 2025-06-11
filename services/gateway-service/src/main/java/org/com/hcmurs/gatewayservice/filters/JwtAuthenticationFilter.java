package org.com.hcmurs.gatewayservice.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.com.hcmurs.gatewayservice.dtos.ApiResponse;
import org.com.hcmurs.gatewayservice.utils.JwtUtil;
import org.jetbrains.annotations.NotNull;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter implements WebFilter {
    JwtUtil jwtUtil;
    RedisTemplate<String, String> redisTemplate;
    ObjectMapper objectMapper;
    PathPatternParser patternParser;

    List<String> PUBLIC_ENDPOINTS = List.of(
            //Swagger
            "/swagger-ui.html",
            "/favicon.ico",
            "/v3/api-docs/**",
            "/webjars/swagger-ui/**",
            //Auth
            "/api/oauth2/authorize/**",
            "/api/auth/register",
            "/api/auth/local-login"
    );

    @NotNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        System.out.println(path);
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        HttpCookie jwtCookie = exchange.getRequest().getCookies().getFirst("token");
        if (jwtCookie == null) {
            return unauthorized(exchange);
        }

        String token = jwtCookie.getValue();

        try {
            String jti = jwtUtil.extractJti(token);
            Boolean isRevoked = redisTemplate.hasKey(jti);
            if (Boolean.TRUE.equals(isRevoked)) {
                return unauthorized(exchange);
            }

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("Authorization", "Bearer " + token)
                    .build();

            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            return chain.filter(modifiedExchange);
        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "http://localhost:3000");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
        exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");

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
