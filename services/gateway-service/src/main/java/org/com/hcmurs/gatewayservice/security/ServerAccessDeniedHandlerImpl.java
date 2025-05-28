package org.com.hcmurs.gatewayservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.com.hcmurs.gatewayservice.api.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

@Component
public class ServerAccessDeniedHandlerImpl implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public ServerAccessDeniedHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        var errorBody = ApiError.<Map<String, Object>>builder()
            .message("Access Denied")
            .reason("You don't have permission to access this resource")
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