package com.hieunn.auth_service.controllers;

import com.hieunn.auth_service.dtos.responses.ApiResponse;
import com.hieunn.auth_service.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/mobile/oauth2")
@RequiredArgsConstructor
public class MobileOAuth2Controller {


    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AuthService authService;

    @Value("${security.oauth2.authorized-redirect-url-frontend}")
    private String authorizedRedirectUrlFrontend;

    @GetMapping("/mobile-google")
    public void mobileGoogleAuth(HttpServletResponse response) throws IOException {
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("mobile-google");

        if (clientRegistration == null) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Client registration not found");
            return;
        }

        // Generate state parameter for security
        String state = Base64.getEncoder().encodeToString(
                java.util.UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)
        );

        String authUrl = clientRegistration.getProviderDetails().getAuthorizationUri() +
                "?client_id=" + clientRegistration.getClientId() +
                "&response_type=code" +
                "&scope=" + URLEncoder.encode(String.join(" ", clientRegistration.getScopes()), StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(clientRegistration.getRedirectUri(), StandardCharsets.UTF_8) +
                "&state=" + state;

        response.sendRedirect(authUrl);
    }
//
//    @GetMapping("/callback")
//    public void handleOAuth2Callback(
//            @RequestParam String code,
//            @RequestParam(required = false) String state,
//            @RequestParam(required = false) String error,
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//
//        if (StringUtils.hasText(error)) {
//            // Handle error case
//            String errorRedirect = "org.com.hcmurs://callback?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
//            response.sendRedirect(errorRedirect);
//            return;
//        }
//
//        try {
//            // Process the OAuth2 code and get user info
//            Map<String, Object> result = authService.processMobileOAuth2Login(code, "mobile-google");
//
//            // Extract tokens/user info
//            String jwtToken = (String) result.get("jwt_token");
//
//            // Redirect back to mobile app with success
//            String successRedirect = "org.com.hcmurs://callback" +
//                    "?success=true" +
//                    "&token=" + URLEncoder.encode(jwtToken != null ? jwtToken : "", StandardCharsets.UTF_8);
//
//            response.sendRedirect(successRedirect);
//
//        } catch (Exception e) {
//            // Handle processing error
//            String errorRedirect = "org.com.hcmurs://callback?error=" +
//                    URLEncoder.encode("authentication_failed", StandardCharsets.UTF_8);
//            response.sendRedirect(errorRedirect);
//        }
//    }

//    @PostMapping("/exchange-token")
//    public ResponseEntity<ApiResponse<Map<String, Object>>> exchangeToken(
//            @RequestParam String code,
//            @RequestParam(defaultValue = "mobile-google") String provider) {
//        try {
//            Map<String, Object> result = authService.processMobileOAuth2Login(code, provider);
//            return ResponseEntity.ok(ApiResponse.success("Token exchanged successfully", result));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(ApiResponse.error(400, "Failed to exchange token: " + e.getMessage()));
//        }
//    }

}