package com.hieunn.auth_service.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.exceptions.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ApiKeyInterceptor implements HandlerInterceptor {
    @Value("${security.api.key}")
    String expectedApiKey;

    @Value("${security.api.header}")
    String header;

    final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String apiKey = request.getHeader(header);
        if (expectedApiKey.equals(apiKey)) {
            return true;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        String json = objectMapper.writeValueAsString(
                ApiResponse.error(
                        ErrorMessage.INVALID_API_KEY.getStatus(),
                        ErrorMessage.INVALID_API_KEY.getMessage()));
        response.getWriter().write(json);
        return false;
    }
}
