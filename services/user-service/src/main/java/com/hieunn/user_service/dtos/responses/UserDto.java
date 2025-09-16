/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hieunn.user_service.models.AuthProvider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
  Long userId;
  String username;
  @JsonIgnore String password;
  String email;
  String name;
  String role;
  AuthProvider authProvider;
  String pictureUrl;
  @JsonIgnore String googleId;
  @JsonIgnore String facebookId;

  @JsonProperty("isStudent")
  boolean isStudent;

  @JsonFormat(pattern = "dd/MM/yyyy")
  LocalDate studentExpiredDate;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime createdAt;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime updatedAt;
}
