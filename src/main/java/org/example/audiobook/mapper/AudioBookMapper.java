package org.example.audiobook.mapper;

import org.example.audiobook.dto.request.audiobook.AudioBookRequestDTO;
import org.example.audiobook.dto.response.AudioBookResponseDTO;
import org.example.audiobook.entity.AudioBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AudioBookMapper {

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "user.id", source = "userId")
    AudioBook toEntity(AudioBookRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "userId", source = "user.id")
    AudioBookResponseDTO toDto(AudioBook entity);
}