/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.exception.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ResponseUtil {
  ObjectMapper objectMapper;

  public void sendErrorResponse(HttpServletResponse response, int status, ErrorMessage errorMessage)
      throws Exception {
    response.setStatus(status);
    response.setContentType("application/json");
    String json =
        objectMapper.writeValueAsString(
            ApiResponse.error(errorMessage.getStatus(), errorMessage.getMessage()));
    response.getWriter().write(json);
  }
}
