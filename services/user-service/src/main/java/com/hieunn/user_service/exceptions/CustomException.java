package com.hieunn.user_service.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException {
    int status;
    String message;

    public CustomException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}