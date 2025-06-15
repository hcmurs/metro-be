package com.hieunn.user_service.utils;

import com.hieunn.user_service.exceptions.CustomException;
import com.hieunn.user_service.exceptions.ErrorMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ValidationUtil {
    JwtUtil jwtUtil;

    public void checkAdmin(String token) {
        String role = jwtUtil.extractRole(token);
        if (!role.equalsIgnoreCase("ROLE_ADMIN")) {
            throw new CustomException(
                    ErrorMessage.UNAUTHORIZED.getStatus(),
                    ErrorMessage.UNAUTHORIZED.getMessage());
        }
    }

    public void checkVerifiedUser(Long userIdFromRequest, String token) {
        if (!userIdFromRequest.equals(jwtUtil.extractUserId(token))) {
            throw new CustomException(
                    ErrorMessage.UNAUTHORIZED.getStatus(),
                    ErrorMessage.UNAUTHORIZED.getMessage());
        }
    }
}
