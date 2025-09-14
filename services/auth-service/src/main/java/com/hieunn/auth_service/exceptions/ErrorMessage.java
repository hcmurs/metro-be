/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.auth_service.exceptions;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
  INTERNAL_SERVER_ERROR(500, "Internal server error"),
  UNAUTHORIZED(401, "Username or password incorrect"),
  LACK_OF_TOKEN(400, "Lack of token"),
  UNSUPPORTED_AUTH_PROVIDER(404, "Unsupported authentication provider: "),
  USER_NOT_FOUND(404, "User does not exist"),
  INCORRECT_USERNAME_OR_PASSWORD(401, "Incorrect username or password"),
  INVALID_API_KEY(401, "Invalid API key");

  int status;
  String message;
  static final Map<Integer, ErrorMessage> BY_CODE = new HashMap<>();

  static {
    for (ErrorMessage e : values()) {
      BY_CODE.put(e.status, e);
    }
  }

  ErrorMessage(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
