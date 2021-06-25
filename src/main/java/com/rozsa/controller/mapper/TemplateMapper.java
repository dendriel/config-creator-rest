package com.rozsa.controller.mapper;

import com.rozsa.controller.dto.BaseDto;
import com.rozsa.model.Template;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TemplateMapper extends BaseMapper<Template, BaseDto>{
}
