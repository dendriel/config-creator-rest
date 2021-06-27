package com.rozsa.controller.mapper;

import com.rozsa.controller.dto.UserDto;
import com.rozsa.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "defaultProjectId", expression = "java(holder.getDefaultProjectId() != null ? holder.getDefaultProjectId().toString() : null)")
    UserDto toDto(User holder);

    @Mapping(target = "defaultProjectId", expression = "java(holder.getDefaultProjectId() != null ? holder.getDefaultProjectId().toString() : null)")
    List<UserDto> toDtos(List<User> holder);
}
