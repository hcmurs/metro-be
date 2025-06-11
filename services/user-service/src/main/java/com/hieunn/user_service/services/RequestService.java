package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.RequestCreationRequest;
import com.hieunn.user_service.dtos.responses.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> findByUser(Long userId, String token);
    RequestDto createRequest(RequestCreationRequest requestCreationRequest, String token);
//    List<RequestDto> findAll(String token);
    List<RequestDto> findAll();
}
