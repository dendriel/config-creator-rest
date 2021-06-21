package com.rozsa.controller.mapper;

import com.rozsa.controller.dto.TemplateDto;
import com.rozsa.model.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "rawId")
    @Mapping(target = "data", expression = "java(new org.bson.json.JsonObject(dto.getData()))")
    Template fromDto(TemplateDto dto);

    @Mapping(source = "rawId", target = "id")
    @Mapping(target = "data", expression = "java(template.getData().getJson())")
    TemplateDto toDto(Template template);

    @Mapping(source = "rawId", target = "id")
    @Mapping(target = "data", expression = "java(template.getData().getJson())")
    List<TemplateDto> toDtos(List<Template> templates);
}
