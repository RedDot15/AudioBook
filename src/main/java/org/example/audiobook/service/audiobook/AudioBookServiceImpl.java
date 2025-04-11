package org.example.audiobook.service.audiobook;

import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.exception.custom.AppException;
import org.example.audiobook.exception.ErrorCode;
import org.example.audiobook.mapper.AudioBookMapper;
import org.example.audiobook.repository.AudioBookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AudioBookServiceImpl implements AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final AudioBookMapper audioBookMapper;

    @Override
    public PageResponse<AudioBookResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findAll(pageable);

        return buildPageResponse(audioBookPage);
    }

    @Override
    public PageResponse<AudioBookResponse> getByCategoryId(UUID categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findByCategoryId(categoryId, pageable);
        if (audioBookPage.isEmpty()) {
            throw new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND);
        }

        return buildPageResponse(audioBookPage);
    }

    @Override
    public PageResponse<AudioBookResponse> getByUserId(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findByUserId(userId, pageable);
        if (audioBookPage.isEmpty()) {
            throw new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND);
        }

        return buildPageResponse(audioBookPage);
    }

    @Override
    public AudioBookResponse getById(UUID id) {
        AudioBook audioBook = audioBookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND));
        return audioBookMapper.toResponse(audioBook);
    }

    private PageResponse<AudioBookResponse> buildPageResponse(Page<AudioBook> page) {
        List<AudioBookResponse> content = page.getContent()
                .stream()
                .map(audioBookMapper::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                content
        );
    }
}
