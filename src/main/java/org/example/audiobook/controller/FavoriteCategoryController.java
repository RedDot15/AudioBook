package org.example.audiobook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.request.category.FavoriteCategoryRequest;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.FavoriteCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/favorite-categories")
@RequiredArgsConstructor
public class FavoriteCategoryController {

    private final FavoriteCategoryService favoriteCategoryService;

    private UUID getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        // Lấy Jwt từ principal
        if (!(authentication.getPrincipal() instanceof Jwt)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT principal");
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        // Lấy claim uid
        String uid = jwt.getClaimAsString("uid");
        if (uid == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing uid claim in token");
        }
        try {
            return UUID.fromString(uid);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user ID in token");
        }
    }

    @GetMapping
    public ResponseObject getFavoriteCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UUID userId = getUserIdFromAuthentication();
        return favoriteCategoryService.getFavoriteCategoriesByUserId(userId, page, size);
    }

    @GetMapping("/audiobooks")
    public ResponseObject getAudioBooksFromFavoriteCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        UUID userId = getUserIdFromAuthentication();
        return favoriteCategoryService.getAudioBooksFromFavoriteCategories(userId, page, size);
    }

    @PostMapping("/add")
    public ResponseObject addFavoriteCategories(@Valid @RequestBody FavoriteCategoryRequest request) {
        UUID userId = getUserIdFromAuthentication();
        request.setUserId(userId);
        return favoriteCategoryService.addFavoriteCategories(request);
    }

    @GetMapping("/exists")
    public ResponseObject checkIfUserHasFavoriteCategories() {
        UUID userId = getUserIdFromAuthentication();
        return favoriteCategoryService.checkUserHasFavorites(userId.toString());
    }
}