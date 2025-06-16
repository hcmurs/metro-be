package com.hieunn.auth_service.handlers;

import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.models.CustomOAuth2User;
import com.hieunn.auth_service.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    final JwtUtil jwtUtil;

    @Value("${security.oauth2.authorized-redirect-url-frontend}")
    String authorizedRedirectUrlFrontend;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        if (!(authentication.getPrincipal() instanceof CustomOAuth2User oauthUser)) {
            response.sendRedirect("http://localhost:3000/login");
            return;
        }

        UserDto userDto = oauthUser.getUserDto();
        String token = jwtUtil.generateToken(userDto);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);

        String cookieHeader = String.format("%s=%s; Max-Age=%d; Path=%s; Secure; HttpOnly; SameSite=None",
                cookie.getName(),
                cookie.getValue(),
                cookie.getMaxAge(),
                cookie.getPath());
        response.setHeader("Set-Cookie", cookieHeader);

        response.sendRedirect(authorizedRedirectUrlFrontend);
    }
}
