package org.example.audiobook.service;

import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.request.category.CategoryItem;
import org.example.audiobook.dto.request.category.FavoriteCategoryRequest;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.Category;
import org.example.audiobook.entity.FavoriteCategory;
import org.example.audiobook.entity.User;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.repository.AudioBookRepository;
import org.example.audiobook.repository.CategoryRepository;
import org.example.audiobook.repository.FavoriteCategoryRepository;
import org.example.audiobook.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoriteCategoryService {

    private final FavoriteCategoryRepository favoriteCategoryRepository;
    private final AudioBookRepository audioBookRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

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
            List<UUID> categoryIds = favoriteCategoryRepository.findCategoryIdsByUserId(userId);

            if (categoryIds.isEmpty()) {
                return new ResponseObject(
                        "success",
                        "No favorite categories found for this user",
                        new PageResponse<>(page, size, 0, 0, List.of())
                );
            }

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

    // Thêm mới favorite categories
    public ResponseObject addFavoriteCategories(FavoriteCategoryRequest request) {
        try {
            // Kiểm tra userId tồn tại
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.getUserId()));

            List<FavoriteCategory> favoriteCategories = new ArrayList<>();

            // Xử lý từng category trong request
            for (CategoryItem item : request.getCategories()) {
                UUID categoryId = item.getCategory_id();
                String categoryName = item.getCategory_name();

                // Kiểm tra categoryId tồn tại
                Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));

                // Kiểm tra xem FavoriteCategory đã tồn tại chưa
                boolean exists = favoriteCategoryRepository
                        .findByUserId(user.getId(), PageRequest.of(0, Integer.MAX_VALUE))
                        .getContent()
                        .stream()
                        .anyMatch(fc -> fc.getCategory().getId().equals(categoryId));

                if (exists) {
                    continue; // Bỏ qua nếu đã tồn tại
                }

                // Tạo FavoriteCategory
                FavoriteCategory favoriteCategory = FavoriteCategory.builder()
                        .categoryName(categoryName)
                        .user(user)
                        .category(category)
                        .createdAt(LocalDateTime.now())
                        .build();

                favoriteCategories.add(favoriteCategory);
            }

            if (favoriteCategories.isEmpty()) {
                return new ResponseObject(
                        "success",
                        "No new favorite categories added (all provided categories already exist)",
                        null
                );
            }

            // Lưu danh sách FavoriteCategory
            favoriteCategoryRepository.saveAll(favoriteCategories);

            List<CategoryItem> responseItems = favoriteCategories.stream()
                    .map(fc -> new CategoryItem(fc.getCategoryName(),
                            fc.getCategory().getId()

                    ))
                    .toList();

            return new ResponseObject(
                    "success",
                    "Favorite categories added successfully",
                    responseItems
            );
        } catch (IllegalArgumentException e) {
            return new ResponseObject(
                    "error",
                    e.getMessage(),
                    null
            );
        } catch (Exception e) {
            return new ResponseObject(
                    "error",
                    "Failed to add favorite categories: " + e.getMessage(),
                    null
            );
        }
    }

    public ResponseObject checkUserHasFavorites(UUID userId) {
        try {
            boolean exists = favoriteCategoryRepository.existsByUserId(userId);
            return new ResponseObject(
                    "success",
                    "Check completed",
                    exists
            );
        } catch (Exception e) {
            return new ResponseObject(
                    "error",
                    "Failed to check favorite categories: " + e.getMessage(),
                    null
            );
        }
    }

}