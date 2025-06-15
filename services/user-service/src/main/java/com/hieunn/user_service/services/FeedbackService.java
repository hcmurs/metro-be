package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.FeedbackCreationRequest;
import com.hieunn.user_service.dtos.responses.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> findByUser(Long userId, String token);
    FeedbackDto createFeedback(FeedbackCreationRequest feedbackCreationRequest, String token);
    List<FeedbackDto> findAll(String token);
}
