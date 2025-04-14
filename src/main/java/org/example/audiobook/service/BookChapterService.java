package org.example.audiobook.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.BookChapterResponse;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.BookChapter;
import org.example.audiobook.mapper.BookChapterMapper;
import org.example.audiobook.repository.AudioBookRepository;
import org.example.audiobook.repository.BookChapterRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookChapterService {

    AudioBookRepository audioBookRepository;
    BookChapterRepository bookChapterRepository;
    BookChapterMapper bookChapterMapper;

    public List<BookChapterResponse> getAllByAudioBookId(UUID audioBookId) {
        // Return all book chapter by audio book ID
        return bookChapterRepository.findAllByAudioBook(audioBookRepository.getReferenceById(audioBookId))
                .stream().map(bookChapterMapper::toResponse)
                .collect(Collectors.toList());
    }

}
