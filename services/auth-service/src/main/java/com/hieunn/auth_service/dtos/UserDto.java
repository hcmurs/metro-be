package com.hieunn.auth_service.dtos;

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
    String email;
    String name;
    String role;
    AuthProvider authProvider;
    String pictureUrl;
    String googleId;
    boolean isStudent;
    LocalDateTime studentExpiredDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
