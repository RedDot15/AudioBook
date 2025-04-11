package org.example.audiobook.mapper;

import org.example.audiobook.dto.request.audiobook.AudioBookRequest;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.entity.AudioBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AudioBookMapper {

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "user.id", source = "userId")
    AudioBook toEntity(AudioBookRequest dto);

    @Mapping(target = "categoryResponse", source = "category")
    @Mapping(target = "userId", source = "user.id")
    AudioBookResponse toResponse(AudioBook entity);
}