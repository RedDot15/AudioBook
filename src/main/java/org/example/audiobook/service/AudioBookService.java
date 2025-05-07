package org.example.audiobook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.AudioBookRequest;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.Category;
import org.example.audiobook.entity.User;
import org.example.audiobook.exception.custom.AppException;
import org.example.audiobook.exception.ErrorCode;
import org.example.audiobook.mapper.AudioBookMapper;
import org.example.audiobook.repository.AudioBookRepository;
import org.example.audiobook.repository.CategoryRepository;
import org.example.audiobook.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AudioBookService {

    private final AudioBookRepository audioBookRepository;
    private final AudioBookMapper audioBookMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    public PageResponse<AudioBookResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findAll(pageable);

        return buildPageResponse(audioBookPage);
    }

    public PageResponse<AudioBookResponse> getBySearch(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findBySearch(search, pageable);

        return buildPageResponse(audioBookPage);
    }


    public PageResponse<AudioBookResponse> getByCategoryId(UUID categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findByCategoryId(categoryId, pageable);
        if (audioBookPage.isEmpty()) {
            throw new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND);
        }

        return buildPageResponse(audioBookPage);
    }

    public PageResponse<AudioBookResponse> getByUserId(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findByUserId(userId, pageable);
        if (audioBookPage.isEmpty()) {
            throw new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND);
        }

        return buildPageResponse(audioBookPage);
    }

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

    @Transactional
    public AudioBookRequest createAudioBook(AudioBookRequest audioBookRequest) {
        Category category = categoryRepository.findById(audioBookRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        User user = userRepository.findById(audioBookRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AudioBook audioBook = AudioBook.builder()
                .title(audioBookRequest.getTitle())
                .author(audioBookRequest.getAuthor())
                .publishedYear(audioBookRequest.getPublishedYear())
                .description(audioBookRequest.getDescription())
                .coverImage(audioBookRequest.getCoverImage())
                .isFree(audioBookRequest.getIsFree())
                .duration(audioBookRequest.getDuration())
                .femaleAudioUrl(audioBookRequest.getFemaleAudioUrl())
                .maleAudioUrl(audioBookRequest.getMaleAudioUrl())
                .category(category)
                .user(user)
                .build();

        audioBook = audioBookRepository.save(audioBook);
        audioBookRequest.setId(audioBook.getId());
        return audioBookRequest;
    }

    @Transactional
    public AudioBookRequest updateAudioBook(UUID id, AudioBookRequest audioBookRequest) {
        AudioBook audioBook = audioBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AudioBook not found"));

        Category category = categoryRepository.findById(audioBookRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        User user = userRepository.findById(audioBookRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        audioBook.setTitle(audioBookRequest.getTitle());
        audioBook.setAuthor(audioBookRequest.getAuthor());
        audioBook.setPublishedYear(audioBookRequest.getPublishedYear());
        audioBook.setDescription(audioBookRequest.getDescription());
        audioBook.setCoverImage(audioBookRequest.getCoverImage());
        audioBook.setIsFree(audioBookRequest.getIsFree());
        audioBook.setDuration(audioBookRequest.getDuration());
        audioBook.setFemaleAudioUrl(audioBookRequest.getFemaleAudioUrl());
        audioBook.setMaleAudioUrl(audioBookRequest.getMaleAudioUrl());
        audioBook.setCategory(category);
        audioBook.setUser(user);

        audioBookRepository.save(audioBook);
        return audioBookRequest;
    }
}
