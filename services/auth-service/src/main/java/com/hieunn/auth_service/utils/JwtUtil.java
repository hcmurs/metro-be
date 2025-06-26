package com.hieunn.auth_service.utils;

import com.hieunn.auth_service.dtos.responses.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.logging.LoggingRebinder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j; // Import Slf4j để log lỗi
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtUtil {
    private final LoggingRebinder loggingRebinder;
    @Value("${security.jwt.secret-key}")
    String secretKey;

    @Value("${security.jwt.expiration}")
    long jwtExpiration;

    Key signingKey;

    public JwtUtil(LoggingRebinder loggingRebinder) {
        this.loggingRebinder = loggingRebinder;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractJti(String token) {
        return extractClaim(token, Claims::getId);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDto userDto) {
        return generateToken(new HashMap<>(), userDto);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDto userDto) {
        return buildToken(extraClaims, userDto, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDto userDto,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDto.getUserId().toString())
                .setId(UUID.randomUUID().toString())
                .claim("role", userDto.getRole())
                .claim("email", userDto.getEmail())
                .claim("username", userDto.getUsername())
                .claim("isStudent", userDto.isStudent())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateToken(String token) {
        try {
            // Parses, validates signature, and checks expiration
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String extractUserId(String token) {
        String userId = extractClaim(token, claims -> claims.get("userId", String.class));
        if (userId != null) {
            return userId;
        }
        return extractClaim(token, Claims::getSubject);
    }
}
