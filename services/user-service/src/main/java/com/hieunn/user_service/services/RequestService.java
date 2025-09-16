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

import com.hieunn.user_service.dtos.requests.RequestCreationRequest;
import com.hieunn.user_service.dtos.responses.RequestDto;
import java.util.List;

public interface RequestService {
  List<RequestDto> findByUserId(Long userId);

  RequestDto create(RequestCreationRequest requestCreationRequest);

  List<RequestDto> findAll();

  RequestDto verify(Long requestId, boolean isApproved, String rejectionReason);
}
