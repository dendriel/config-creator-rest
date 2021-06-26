package com.rozsa.controller.mapper;

import com.rozsa.controller.dto.UserDto;
import com.rozsa.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "defaultProjectId", expression = "java(holder.getDefaultProjectId().toString())")
    UserDto toDto(User holder);

    @Mapping(target = "defaultProjectId", expression = "java(holder.getDefaultProjectId().toString())")
    List<UserDto> toDtos(List<User> holder);
}
