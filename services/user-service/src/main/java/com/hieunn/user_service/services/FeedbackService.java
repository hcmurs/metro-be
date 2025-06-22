package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.FeedbackCreationRequest;
import com.hieunn.user_service.dtos.responses.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> findByUserId(Long userId);
    FeedbackDto create(FeedbackCreationRequest feedbackCreationRequest);
    List<FeedbackDto> findAll();
    FeedbackDto reply(Long feedbackId, String content);
}
