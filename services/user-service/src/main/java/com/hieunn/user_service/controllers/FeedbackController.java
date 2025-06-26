package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.FeedbackCreationRequest;
import com.hieunn.user_service.dtos.requests.FeedbackReplyRequest;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.responses.FeedbackDto;
import com.hieunn.user_service.services.FeedbackService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/feedbacks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackController {
    FeedbackService feedbackService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<FeedbackDto>>> findFeedbackByUserId(
            @PathVariable Long userId) {
        List<FeedbackDto> feedbacks = feedbackService.findByUserId(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(feedbacks));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FeedbackDto>> create(
            @Valid @RequestBody FeedbackCreationRequest feedbackCreationRequest) {
        FeedbackDto feedback = feedbackService.create(feedbackCreationRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(feedback));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FeedbackDto>>> findAll() {
        List<FeedbackDto> feedbacks = feedbackService.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(feedbacks));
    }

    @PostMapping("/reply")
    public ResponseEntity<ApiResponse<FeedbackDto>> reply(
            @RequestBody @Valid FeedbackReplyRequest feedbackReplyRequest) {
        FeedbackDto feedback = feedbackService.reply(
                feedbackReplyRequest.getFeedbackId(),
                feedbackReplyRequest.getContent());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(feedback));
    }
}
