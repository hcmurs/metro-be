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
            @PathVariable Long userId) {
        List<RequestDto> requests = requestService.findByUserId(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(requests));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RequestDto>> create(
            @Valid @RequestBody RequestCreationRequest requestCreationRequest) {
        RequestDto request = requestService.create(requestCreationRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<RequestDto>> verify(
            @RequestParam Long requestId,
            @RequestParam boolean isApproved,
            @RequestParam String rejectionReason) {
        RequestDto request = requestService.verify(requestId, isApproved, rejectionReason);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RequestDto>>> findAll() {
        List<RequestDto> requests = requestService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(requests));
    }
}
