package com.hieunn.user_service.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hieunn.user_service.models.AuthProvider;
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
    @JsonIgnore
    String password;
    String email;
    String name;
    String role;
    AuthProvider authProvider;
    String pictureUrl;
    @JsonIgnore
    String googleId;
    Boolean isStudent;
    LocalDateTime studentExpiredDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}