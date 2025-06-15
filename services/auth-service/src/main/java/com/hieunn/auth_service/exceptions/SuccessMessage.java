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
