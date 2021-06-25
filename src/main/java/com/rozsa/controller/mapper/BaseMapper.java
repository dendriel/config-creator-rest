package com.rozsa.controller.mapper;

import org.mapstruct.Mapping;

import java.util.List;

public interface BaseMapper<TModel, TDto> {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "rawId")
    @Mapping(target = "data", expression = "java(new org.bson.json.JsonObject(dto.getData()))")
    TModel fromDto(TDto dto);

    @Mapping(source = "rawId", target = "id")
    @Mapping(target = "data", expression = "java(template.getData().getJson())")
    TDto toDto(TModel template);

    @Mapping(source = "rawId", target = "id")
    @Mapping(target = "data", expression = "java(template.getData().getJson())")
    List<TDto> toDtos(List<TModel> templates);
}
