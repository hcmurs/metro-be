package com.hieunn.user_service.dtos.requests;

import com.hieunn.user_service.models.AuthProvider;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialLoginRequest {
    @Email
    String email;
    String name;
    String providerId;
    String pictureUrl;
    AuthProvider authProvider;
}
