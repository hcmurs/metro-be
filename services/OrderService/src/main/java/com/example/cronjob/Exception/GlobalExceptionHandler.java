package com.example.cronjob.Exception;
import com.example.cronjob.DTO.Response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
    }

    // Handle constraint violations (e.g. @Min, @NotBlank outside of @Valid)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Void> handleConstraintViolation(ConstraintViolationException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    // Handle bad JSON body or type mismatches
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleInvalidJson(HttpMessageNotReadableException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Invalid request format");
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgument(IllegalArgumentException ex) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

//     Handle custom exceptions (optional)
    @ExceptionHandler({EntityNotFoundException.class})
    public ApiResponse<Void> handleNotFound(RuntimeException ex) {
        return ApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
}

    // Fallback for unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // hoặc log lỗi
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred");
    }
}
