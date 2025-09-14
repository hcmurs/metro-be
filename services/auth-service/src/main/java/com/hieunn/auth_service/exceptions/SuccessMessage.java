/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessMessage {
  USER_CREATED("User created successfully"),
  USER_UPDATED("User updated successfully"),
  USER_DELETED("User deleted successfully");

  private String message;

  SuccessMessage(String message) {
    this.message = message;
  }
}
