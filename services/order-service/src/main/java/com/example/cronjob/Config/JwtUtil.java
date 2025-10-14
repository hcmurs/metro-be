/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JwtUtil {
  @Value("${security.jwt.secret-key}")
  String secretKey;

  Key signingKey;

  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    signingKey = Keys.hmacShaKeyFor(keyBytes);
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
  }

  public Long extractUserId(String token) {
    Long userId = Long.valueOf(extractAllClaims(token).getSubject());
    return userId != null ? Long.valueOf(userId) : null;
  }

  public String extractRole(String token) {
    return extractAllClaims(token).get("role", String.class);
  }
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }
  public boolean validateToken(String token) {
    try {
      Claims claims = extractAllClaims(token);
      // Có thể kiểm tra thêm "subject" hoặc "expiration" nếu muốn
      return true;
    } catch (ExpiredJwtException e) {
      log.warn("JWT expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.warn("Unsupported JWT: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.warn("Malformed JWT: {}", e.getMessage());
    } catch (SignatureException e) {
      log.warn("Invalid signature: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.warn("JWT claims string is empty: {}", e.getMessage());
    } catch (Exception e) {
      log.error("Unexpected JWT validation error: {}", e.getMessage());
    }

    return false;
  }

  public List<GrantedAuthority> extractAuthorities(String token) {
    Claims claims = extractAllClaims(token);
    String role = claims.get("role", String.class);

    return List.of(new SimpleGrantedAuthority(role));
  }
}
