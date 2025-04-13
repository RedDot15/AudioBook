package org.example.audiobook.controller;

import lombok.RequiredArgsConstructor;
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
}