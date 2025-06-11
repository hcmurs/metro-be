package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.FeedbackCreationRequest;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.responses.FeedbackDto;
import com.hieunn.user_service.services.FeedbackService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackController {
    FeedbackService feedbackService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<FeedbackDto>>> findRequestByUser(
            @RequestHeader("Authorization") String token,
            @PathVariable Long userId
    ) {
        List<FeedbackDto> requests = feedbackService.findByUser(userId, token.substring(7));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(requests));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FeedbackDto>> createRequest(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody FeedbackCreationRequest feedbackCreationRequest
    ) {
        FeedbackDto request = feedbackService.createFeedback(feedbackCreationRequest, token.substring(7));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FeedbackDto>>> findAll(
            @RequestHeader("Authorization") String token
    ) {
        List<FeedbackDto> requests = feedbackService.findAll(token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(requests));
    }
}
