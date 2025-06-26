package com.hieunn.user_service.mappers;

import com.hieunn.user_service.dtos.responses.UserDto;
import com.hieunn.user_service.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "googleId", ignore = true)
    @Mapping(target = "facebookId", ignore = true)
    UserDto toUserDto(User user);
}
