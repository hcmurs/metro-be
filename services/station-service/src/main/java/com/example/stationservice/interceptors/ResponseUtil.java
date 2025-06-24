package com.example.stationservice.interceptors;

import com.example.stationservice.Exception.ErrorMessage;
import com.example.stationservice.config.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseUtil {
    ObjectMapper objectMapper;

    public void sendErrorResponse(HttpServletResponse response, int status, ErrorMessage errorMessage) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json");
        String json = objectMapper.writeValueAsString(ApiResponse.error(
                errorMessage.getStatus(),
                errorMessage.getMessage()
        ));
        response.getWriter().write(json);
    }
}
