package com.hieunn.auth_service.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Value("${security.oauth2.authorized-redirect-url-frontend}")
    String redirectUrl;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        if (exception.getMessage().contains("access_denied")) {
            redirectUrl += "?error=access_denied";
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}