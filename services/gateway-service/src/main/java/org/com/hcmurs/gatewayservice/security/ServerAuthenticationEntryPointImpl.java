package org.com.hcmurs.gatewayservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.com.hcmurs.gatewayservice.api.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServerAuthenticationEntryPointImpl implements ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        var errorBody = ApiError.<Map<String, Object>>builder()
            .message("Authentication Required")
            .reason("You must be authenticated to access this resource")
            .statusCode(HttpStatus.FORBIDDEN.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", exchange.getRequest().getURI().getPath()
            ))
            .build();

        try {
            var buffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorBody));
            return response.writeWith(Mono.just(buffer));
        } catch (IOException ioException) {
            return Mono.error(ioException);
        }
    }
}