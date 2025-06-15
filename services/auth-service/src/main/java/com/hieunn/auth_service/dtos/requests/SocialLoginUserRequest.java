package com.hieunn.auth_service.dtos.requests;

import com.hieunn.auth_service.models.AuthProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialLoginUserRequest {
    String email;
    String name;
    String providerId;
    String pictureUrl;
    AuthProvider authProvider;
}
