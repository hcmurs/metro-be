package com.hieunn.auth_service.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hieunn.auth_service.models.AuthProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long userId;
    String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String password;
    String email;
    String name;
    String role;
    AuthProvider authProvider;
    String pictureUrl;
    @JsonIgnore
    String googleId;
    boolean isStudent;
    LocalDateTime studentExpiredDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
