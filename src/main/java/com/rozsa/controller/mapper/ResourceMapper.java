package com.rozsa.controller.mapper;

import com.rozsa.controller.dto.BaseDto;
import com.rozsa.model.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper extends BaseMapper<Resource, BaseDto> {
}
