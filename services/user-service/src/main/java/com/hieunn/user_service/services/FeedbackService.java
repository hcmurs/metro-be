/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
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
