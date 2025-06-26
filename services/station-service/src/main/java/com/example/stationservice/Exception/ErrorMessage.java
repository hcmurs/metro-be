package com.example.stationservice.Exception;

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
    USERNAME_ALREADY_EXISTS(400, "Username already exists"),
    INVALID_TOKEN(401, "Invalid JWT token"),
    INVALID_API_KEY(401, "Invalid API key"),
    UNAUTHORIZED(403, "You do not have permission"),
    ALREADY_LOGOUT(401, "Already logout"),
    LACK_OF_TOKEN(400, "Lack of JWT token"),
    ALREADY_REQUESTED(400, "Already requested"),
    EMAIL_NOT_VERIFIED(401, "Email is not verified"),
    INCORRECT_USERNAME_OR_PASSWORD(401, "Incorrect username or password"),
    NEW_PASSWORD_EQUALS_OLD_PASSWORD(400, "New password are the same with old password"),

    REQUEST_NOT_FOUND(404, "Request does not exist");

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
