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
