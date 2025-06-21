package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.RequestCreationRequest;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.responses.RequestDto;
import com.hieunn.user_service.services.RequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/requests")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RequestController {
    RequestService requestService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<RequestDto>>> findRequestByUserId(
            @RequestHeader("Authorization") String token,
            @PathVariable Long userId) {
        List<RequestDto> requests = requestService.findByUserId(userId, token.substring(7));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(requests));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RequestDto>> createRequest(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody RequestCreationRequest requestCreationRequest) {
        RequestDto request = requestService.createRequest(requestCreationRequest, token.substring(7));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyRequest(
            @RequestHeader("Authorization") String token,
            @RequestParam Long requestId,
            @RequestParam boolean isApproved) {
        requestService.verifyRequest(requestId, isApproved, token.substring(7));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Request has been verified"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RequestDto>>> findAll(
            @RequestHeader("Authorization") String token) {
        List<RequestDto> requests = requestService.findAll(token.substring(7));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(requests));
    }
}
