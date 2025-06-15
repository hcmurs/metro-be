package com.hieunn.user_service.mappers;

import com.hieunn.user_service.dtos.responses.FeedbackDto;
import com.hieunn.user_service.models.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(source = "user.userId", target = "userId")
    FeedbackDto toFeedbackDto(Feedback feedback);
}
