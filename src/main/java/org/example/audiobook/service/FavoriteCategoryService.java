package org.example.audiobook.service;

import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.FavoriteCategory;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.repository.AudioBookRepository;
import org.example.audiobook.repository.FavoriteCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteCategoryService {

    private final FavoriteCategoryRepository favoriteCategoryRepository;
    private final AudioBookRepository audioBookRepository;

    // Lấy danh sách category yêu thích của user
    public ResponseObject getFavoriteCategoriesByUserId(UUID userId, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<FavoriteCategory> favoriteCategoryPage = favoriteCategoryRepository
                    .findByUserId(userId, pageable);

            PageResponse<FavoriteCategory> pageResponse = new PageResponse<>(
                    favoriteCategoryPage.getNumber(),
                    favoriteCategoryPage.getSize(),
                    favoriteCategoryPage.getTotalElements(),
                    favoriteCategoryPage.getTotalPages(),
                    favoriteCategoryPage.getContent()
            );

            return new ResponseObject(
                    "success",
                    "Favorite categories retrieved successfully",
                    pageResponse
            );
        } catch (Exception e) {
            return new ResponseObject(
                    "error",
                    "Failed to retrieve favorite categories: " + e.getMessage(),
                    null
            );
        }
    }

    // Lấy danh sách audiobook từ các category yêu thích của user
    public ResponseObject getAudioBooksFromFavoriteCategories(UUID userId, int page, int size) {
        try {
            // Lấy danh sách categoryId từ FavoriteCategory của user
            List<UUID> categoryIds = favoriteCategoryRepository.findCategoryIdsByUserId(userId);

            if (categoryIds.isEmpty()) {
                return new ResponseObject(
                        "success",
                        "No favorite categories found for this user",
                        new PageResponse<>(page, size, 0, 0, List.of())
                );
            }

            // Lấy danh sách audiobook theo categoryIds với phân trang
            Pageable pageable = PageRequest.of(page, size);
            Page<AudioBook> audioBookPage = audioBookRepository
                    .findByCategoryIds(categoryIds, pageable);

            PageResponse<AudioBook> pageResponse = new PageResponse<>(
                    audioBookPage.getNumber(),
                    audioBookPage.getSize(),
                    audioBookPage.getTotalElements(),
                    audioBookPage.getTotalPages(),
                    audioBookPage.getContent()
            );

            return new ResponseObject(
                    "success",
                    "Audiobooks retrieved successfully",
                    pageResponse
            );
        } catch (Exception e) {
            return new ResponseObject(
                    "error",
                    "Failed to retrieve audiobooks: " + e.getMessage(),
                    null
            );
        }
    }
}