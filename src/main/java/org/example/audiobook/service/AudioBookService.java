package org.example.audiobook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.request.audiobook.AudioBookCreateRequest;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.Category;
import org.example.audiobook.entity.User;
import org.example.audiobook.exception.custom.AppException;
import org.example.audiobook.exception.ErrorCode;
import org.example.audiobook.mapper.AudioBookMapper;
import org.example.audiobook.repository.*;
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
    private final FavoriteCategoryRepository favoriteCategoryRepository;


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

    public PageResponse<AudioBookResponse> getBySearchWithUser(UUID userId, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findByTextsearchAndUserId(search, userId,  pageable);

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

    public PageResponse<AudioBookResponse> getByCategoryIds(UUID userId, int page, int size) {

        List<UUID> categoryIds = favoriteCategoryRepository.findCategoryIdsByUserId(userId);
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_CATEGORY_IDS);
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findByCategoryIds(categoryIds, pageable);

        if (audioBookPage.isEmpty()) {
            throw new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND);
        }

        return buildPageResponse(audioBookPage);
    }

    public PageResponse<AudioBookResponse> getAllByPublishedYearDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<AudioBook> audioBookPage = audioBookRepository.findAllByOrderByPublishedYearDesc(pageable);

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
    public AudioBookCreateRequest createAudioBook(AudioBookCreateRequest audioBookRequest) {
        // Tìm Category
        Category category = categoryRepository.findById(audioBookRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Tìm User
        User user = userRepository.findById(audioBookRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo AudioBook
        AudioBook audioBook = AudioBook.builder()
                .title(audioBookRequest.getTitle())
                .author(audioBookRequest.getAuthor())
                .publishedYear(audioBookRequest.getPublishedYear())
                .textContent(audioBookRequest.getTextContent())
                .description(audioBookRequest.getDescription())
                .coverImage(audioBookRequest.getCoverImage())
                .isFree(audioBookRequest.getIsFree())
                .duration(audioBookRequest.getDuration())
                .femaleAudioUrl(audioBookRequest.getFemaleAudioUrl())
                .maleAudioUrl(audioBookRequest.getMaleAudioUrl())
                .category(category)
                .user(user)
                .build();

        // Lưu AudioBook
        audioBook = audioBookRepository.save(audioBook);

        // Cập nhật ID cho AudioBookRequest
        audioBookRequest.setId(audioBook.getId());
        return audioBookRequest;
    }

    @Transactional
    public AudioBookCreateRequest updateAudioBook(UUID id, AudioBookCreateRequest audioBookCreateRequest) {
        // Tìm AudioBook
        AudioBook audioBook = audioBookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND));

        // Tìm Category
        Category category = categoryRepository.findById(audioBookCreateRequest.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // Tìm User
        User user = userRepository.findById(audioBookCreateRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Cập nhật thông tin AudioBook
        audioBook.setTitle(audioBookCreateRequest.getTitle());
        audioBook.setAuthor(audioBookCreateRequest.getAuthor());
        audioBook.setPublishedYear(audioBookCreateRequest.getPublishedYear());
        audioBook.setDescription(audioBookCreateRequest.getDescription());
        audioBook.setCoverImage(audioBookCreateRequest.getCoverImage());
        audioBook.setIsFree(audioBookCreateRequest.getIsFree());
        audioBook.setDuration(audioBookCreateRequest.getDuration());
        audioBook.setFemaleAudioUrl(audioBookCreateRequest.getFemaleAudioUrl());
        audioBook.setMaleAudioUrl(audioBookCreateRequest.getMaleAudioUrl());
        audioBook.setTextContent(audioBookCreateRequest.getTextContent());
        audioBook.setCategory(category);

        audioBook.setUser(user);

        // Lưu AudioBook
        audioBookRepository.save(audioBook);

        return audioBookCreateRequest;
    }
    @Transactional
    public void deleteAudioBook(UUID id) {
        // Tìm AudioBook
        AudioBook audioBook = audioBookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUDIO_BOOK_NOT_FOUND));

        // Xóa AudioBook
        audioBookRepository.delete(audioBook);
    }
}
