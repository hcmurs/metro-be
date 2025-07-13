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
