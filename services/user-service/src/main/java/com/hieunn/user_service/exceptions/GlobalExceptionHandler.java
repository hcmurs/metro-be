package com.hieunn.user_service.exceptions;

import com.hieunn.user_service.dtos.responses.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.error(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Void>> handleNullPointerException(NullPointerException e) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.error(500, e.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException e) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.error(
                        ErrorMessage.INVALID_TOKEN.getStatus(),
                        ErrorMessage.INVALID_TOKEN.getMessage())
                );
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.error(
                        ErrorMessage.ALREADY_LOGOUT.getStatus(),
                        ErrorMessage.ALREADY_LOGOUT.getMessage())
                );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Pattern pattern = Pattern.compile("Key \\((\\w+)\\)=\\((.*?)\\) already exists");
        Matcher matcher = pattern.matcher(e.getMessage());

        String message = "";
        if (matcher.find()) {
            String field = matcher.group(1); // username
            message = field + " already exists";
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.error(400, message));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.error(
                        ErrorMessage.UNAUTHORIZED.getStatus(),
                        ErrorMessage.UNAUTHORIZED.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.error(500, e.getMessage()));
    }
}
