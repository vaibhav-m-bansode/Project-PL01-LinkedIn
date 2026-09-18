package com.vaibhavbansode.userService.mapper;

import com.vaibhavbansode.userService.dto.SignupRequest;
import com.vaibhavbansode.userService.dto.UserDto;
import com.vaibhavbansode.userService.entity.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);

    User toEntity(SignupRequest signupRequest);
}