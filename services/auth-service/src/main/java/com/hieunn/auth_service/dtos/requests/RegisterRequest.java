package com.hieunn.auth_service.dtos.requests;

import com.hieunn.auth_service.models.AuthProvider;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Email
    String email;
    String username;
    String password;
    AuthProvider authProvider;
}
