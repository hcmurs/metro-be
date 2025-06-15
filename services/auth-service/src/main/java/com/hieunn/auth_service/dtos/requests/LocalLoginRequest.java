package com.hieunn.auth_service.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalLoginRequest {
    String usernameOrEmail;
    String password;
}
