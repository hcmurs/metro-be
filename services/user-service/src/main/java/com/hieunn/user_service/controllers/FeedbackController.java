package com.hieunn.user_service.controllers;

import com.hieunn.user_service.dtos.requests.FeedbackCreationRequest;
import com.hieunn.user_service.dtos.requests.FeedbackReplyRequest;
import com.hieunn.user_service.dtos.responses.ApiResponse;
import com.hieunn.user_service.dtos.responses.FeedbackDto;
import com.hieunn.user_service.services.FeedbackService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
