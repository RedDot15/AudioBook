package org.example.audiobook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.request.category.FavoriteCategoryRequest;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.FavoriteCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/favorite-categories")
@RequiredArgsConstructor
public class FavoriteCategoryController {

    private final FavoriteCategoryService favoriteCategoryService;

    @GetMapping("/{userId}")
    public ResponseObject getFavoriteCategories(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return favoriteCategoryService.getFavoriteCategoriesByUserId(userId, page, size);
    }

    @GetMapping("/{userId}/audiobooks")
    public ResponseObject getAudioBooksFromFavoriteCategories(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return favoriteCategoryService.getAudioBooksFromFavoriteCategories(userId, page, size);
    }

    @PostMapping("/add")
    public ResponseObject addFavoriteCategories(@Valid @RequestBody FavoriteCategoryRequest request) {
        return favoriteCategoryService.addFavoriteCategories(request);
    }

    @GetMapping("/{userId}/exists")
    public ResponseObject checkIfUserHasFavoriteCategories(@PathVariable UUID userId) {
        return favoriteCategoryService.checkUserHasFavorites(userId);
    }

}