/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.hieunn.auth_service.dtos.responses.UserDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Builder
public class CustomOAuth2User implements OAuth2User {
  UserDto userDto;
  Map<String, Object> attributes;

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (userDto != null && userDto.getRole() != null && !userDto.getRole().isBlank()) {
      return List.of(new SimpleGrantedAuthority(userDto.getRole()));
    }
    return Collections.emptyList();
  }

  @Override
  public String getName() {
    return String.valueOf(userDto.getUserId());
  }
}
