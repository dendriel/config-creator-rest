package com.rozsa.controller.mapper;

import org.mapstruct.Mapping;

import java.util.List;

public interface BaseMapper<TModel, TDto> {

    @Mapping(target = "id", expression = "java(dto.getId() != null ? new org.bson.types.ObjectId(dto.getId()) : null)")
    @Mapping(target = "data", expression = "java(new org.bson.json.JsonObject(dto.getData()))")
    TModel fromDto(TDto dto);

    @Mapping(target = "id", expression = "java(holder.getId().toString())")
    @Mapping(target = "data", expression = "java(holder.getData().getJson())")
    TDto toDto(TModel holder);

    @Mapping(target = "id", expression = "java(holder.getId().toString())")
    @Mapping(target = "data", expression = "java(holder.getData().getJson())")
    List<TDto> toDtos(List<TModel> holder);
}
