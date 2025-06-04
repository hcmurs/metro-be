package com.hieunn.auth_service.services;

import com.hieunn.auth_service.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    JwtUtil jwtUtil;
    RedisTemplate<String, String> redisTemplate;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return;

        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();

                try {
                    String jti = jwtUtil.extractJti(token);
                    Date exp = jwtUtil.extractExpiration(token);

                    long ttl = (exp.getTime() - System.currentTimeMillis()) / 1000;
                    if (ttl > 0) {
                        String redisKey = "blacklist:" + jti;
                        redisTemplate.opsForValue().set(redisKey, "1", Duration.ofSeconds(ttl));
                    }
                } catch (Exception ignored) {

                }

                Cookie expiredCookie = new Cookie("token", null);
                expiredCookie.setHttpOnly(true);
                expiredCookie.setSecure(true);
                expiredCookie.setPath("/");
                expiredCookie.setMaxAge(0);
                response.addCookie(expiredCookie);

                break;
            }
        }
    }

}
