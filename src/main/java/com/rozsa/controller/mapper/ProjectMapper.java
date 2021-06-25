package com.rozsa.controller.mapper;

import com.rozsa.controller.dto.BaseDto;
import com.rozsa.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends BaseMapper<Project, BaseDto> {
}
