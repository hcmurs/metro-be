package com.hieunn.user_service.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorMessage {
    USER_NOT_FOUND(404, "User does not exist"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    EMAIL_ALREADY_EXISTS(400, "Email already exists"),
    INVALID_TOKEN(401, "Invalid JWT token"),
    INVALID_API_KEY(401, "Invalid API key"),
    UNAUTHORIZED(403, "You do not have permission"),
    ALREADY_LOGOUT(401, "Already logout"),
    LACK_OF_TOKEN(400, "Lack of JWT token"),
    ALREADY_REQUESTED(400, "Already requested"),
    EMAIL_ALREADY_USED(400, "Email already used"),
    INCORRECT_USERNAME_OR_PASSWORD(401, "Incorrect username or password");

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
