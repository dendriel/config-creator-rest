package com.rozsa.controller.mapper;

import com.rozsa.controller.dto.ConfigurationDto;
import com.rozsa.model.Configuration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfigurationMapper {
    @Mapping(target = "id", expression = "java(dto.getId() != null && !dto.getId().equals(\"\")? new org.bson.types.ObjectId(dto.getId()) : null)")
    @Mapping(target = "projectId", expression = "java(dto.getProjectId() != null && !dto.getProjectId().equals(\"\")? new org.bson.types.ObjectId(dto.getProjectId()) : null)")
    @Mapping(target = "requestedBy", ignore = true)
    Configuration fromDto(ConfigurationDto dto);

    @Mapping(target = "id", expression = "java(dto.getId() != null && !dto.getId().equals(\"\")? new org.bson.types.ObjectId(dto.getId()) : null)")
    @Mapping(target = "projectId", expression = "java(dto.getProjectId() != null && !dto.getProjectId().equals(\"\")? new org.bson.types.ObjectId(dto.getProjectId()) : null)")
    @Mapping(target = "requestedBy", ignore = true)
    List<Configuration> fromDtos(List<ConfigurationDto> dto);

    @Mapping(target = "id", expression = "java(holder.getId().toString())")
    @Mapping(target = "projectId", expression = "java(holder.getProjectId().toString())")
    @Mapping(target = "requestedBy", expression = "java(holder.getRequestedBy().toString())")
    ConfigurationDto toDto(Configuration holder);

    @Mapping(target = "id", expression = "java(holder.getId().toString())")
    @Mapping(target = "projectId", expression = "java(holder.getProjectId().toString())")
    @Mapping(target = "requestedBy", expression = "java(holder.getRequestedBy().toString())")
    List<ConfigurationDto> toDtos(List<Configuration> holder);
}
