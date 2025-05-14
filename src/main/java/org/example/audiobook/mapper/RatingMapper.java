package org.example.audiobook.mapper;

import org.example.audiobook.dto.request.audiobook.AudioBookRequest;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.RatingResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "userResponse", source = "user")
    RatingResponse toResponse(Rating entity);
}