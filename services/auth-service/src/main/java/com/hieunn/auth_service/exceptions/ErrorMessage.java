package com.hieunn.auth_service.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

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
