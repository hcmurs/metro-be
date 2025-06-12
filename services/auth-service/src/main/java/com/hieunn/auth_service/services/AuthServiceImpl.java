package com.hieunn.auth_service.services;

import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.dtos.requests.RegisterRequest;
import com.hieunn.auth_service.exceptions.CustomException;
import com.hieunn.auth_service.exceptions.ErrorMessage;
import com.hieunn.auth_service.feignClients.UserServiceClient;
import com.hieunn.auth_service.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    JwtUtil jwtUtil;
    RedisTemplate<String, String> redisTemplate;
    UserServiceClient userServiceClient;
    PasswordEncoder passwordEncoder;
    HttpServletResponse response;

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

    @Override
    public ApiResponse<UserDto> register(RegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return userServiceClient.register(registerRequest);
    }

    @Override
    public ApiResponse<UserDto> processLocalLogin(LocalLoginRequest localLoginRequest) {
        ApiResponse<UserDto> apiResponse = userServiceClient.processLocalLogin(localLoginRequest);

        if (apiResponse.getData() != null) {
            UserDto userDto = apiResponse.getData();
            if (!passwordEncoder.matches(localLoginRequest.getPassword(), userDto.getPassword())) {
                throw new CustomException(
                        ErrorMessage.INCORRECT_USERNAME_OR_PASSWORD.getStatus(),
                        ErrorMessage.INCORRECT_USERNAME_OR_PASSWORD.getMessage()
                );
            }
            userDto.setPassword(null);

            String token = jwtUtil.generateToken(userDto);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);
        }

        return apiResponse;
    }
}