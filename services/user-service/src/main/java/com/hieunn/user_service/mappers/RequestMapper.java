/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.mappers;

import com.hieunn.user_service.dtos.responses.RequestDto;
import com.hieunn.user_service.models.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestMapper {
  @Mapping(source = "user.userId", target = "userId")
  RequestDto toRequestDto(Request request);
}
