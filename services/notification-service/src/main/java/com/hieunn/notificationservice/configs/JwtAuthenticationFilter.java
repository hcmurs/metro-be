/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = authHeader.substring(7);

    if (!jwtUtil.validateToken(jwt)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      Map<String, Object> errorDetails = new HashMap<>();
      errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
      errorDetails.put("error", "Unauthorized");
      errorDetails.put("message", "Invalid JWT token");
      errorDetails.put("path", request.getRequestURI());

      new ObjectMapper().writeValue(response.getOutputStream(), errorDetails);
      return;
    }

    Long userId = jwtUtil.extractUserId(jwt);
    if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      List<GrantedAuthority> authorities = jwtUtil.extractAuthorities(jwt);
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(userId, jwt, authorities);
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    filterChain.doFilter(request, response);
  }
}
