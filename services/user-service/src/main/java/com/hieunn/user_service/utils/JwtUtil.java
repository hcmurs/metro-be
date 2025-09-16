/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtil {
  @Value("${security.jwt.secret-key}")
  String secretKey;

  Key signingKey;

  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    signingKey = Keys.hmacShaKeyFor(keyBytes);
  }

  public Claims extractAllClaims(String token)
      throws ExpiredJwtException, IllegalArgumentException {
    return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
  }

  public Long extractUserId(String token) {
    String userId = extractAllClaims(token).getSubject();
    return Long.parseLong(userId);
  }

  public String extractRole(String token) {
    return extractAllClaims(token).get("role", String.class);
  }

  public String extractEmail(String token) {
    return extractAllClaims(token).get("email", String.class);
  }

  public boolean validateToken(String token) {
    try {
      extractAllClaims(token);
      return true;
    } catch (ExpiredJwtException | IllegalArgumentException e) {
      return false;
    }
  }
}
