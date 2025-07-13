package com.hieunn.auth_service.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.hieunn.auth_service.dtos.requests.LocalLoginRequest;
import com.hieunn.auth_service.dtos.requests.SocialLoginUserRequest;
import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.dtos.responses.TokenResponse;
import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.feignClients.UserServiceClient;
import com.hieunn.auth_service.models.AuthProvider;
import com.hieunn.auth_service.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    final JwtUtil jwtUtil;
    final RedisTemplate<String, String> redisTemplate;
    final UserServiceClient userServiceClient;
    final HttpServletResponse response;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String googleClientId;


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
    public ApiResponse<UserDto> processLocalLogin(LocalLoginRequest localLoginRequest) {
        ApiResponse<UserDto> apiResponse = userServiceClient.processLocalLogin(localLoginRequest);

        if (apiResponse.getData() != null) {
            UserDto userDto = apiResponse.getData();

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


    @Override
    public ApiResponse<TokenResponse> processGoogleLogin(String idToken) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                return ApiResponse.success(null, "Invalid Google ID token");
            }

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String providerId = payload.getSubject();

            SocialLoginUserRequest socialLoginRequest = new SocialLoginUserRequest(
                    email, name, providerId, pictureUrl, AuthProvider.GOOGLE);
            UserDto user = userServiceClient.processSocialLogin(socialLoginRequest).getData();

            String accessToken = jwtUtil.generateToken(user);

            return ApiResponse.success(new TokenResponse(accessToken));
        } catch (Exception e) {
            log.error("Error validating Google ID token", e);
            return ApiResponse.success(null, "Authentication failed");
        }
    }

    @Override
    public ApiResponse<UserDto> getUserProfileFromToken(String authorizationHeader) {
        try {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ApiResponse.<UserDto>builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("Invalid Authorization header format")
                        .build();
            }

            String token = authorizationHeader.substring(7);

            if (!jwtUtil.validateToken(token)) {
                return ApiResponse.<UserDto>builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("Invalid or expired token")
                        .build();
            }

            ApiResponse<UserDto> userResponse = userServiceClient.getUserProfile("Bearer " + token);

            if (userResponse.getData() != null) {
                return ApiResponse.success(userResponse.getData());
            } else {
                return ApiResponse.<UserDto>builder()
                        .status(userResponse.getStatus())
                        .message(userResponse.getMessage())
                        .build();
            }

        } catch (Exception e) {
            log.error("Error fetching user profile from token", e);
            return ApiResponse.<UserDto>builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Failed to fetch user profile")
                    .build();
        }
    }


}