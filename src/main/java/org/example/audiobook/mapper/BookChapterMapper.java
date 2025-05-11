package org.example.audiobook.mapper;

import org.example.audiobook.dto.request.audiobook.AudioBookRequest;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.BookChapterResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.BookChapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookChapterMapper {

    BookChapterResponse toResponse(BookChapter entity);
}