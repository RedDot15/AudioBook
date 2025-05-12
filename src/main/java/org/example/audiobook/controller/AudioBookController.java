// AudioBookController.java
package org.example.audiobook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.request.audiobook.AudioBookCreateRequest;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.AudioBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.example.audiobook.helper.ResponseBuilder.buildResponse;

@RestController
@RequestMapping("/api/audio-book")
@RequiredArgsConstructor
public class AudioBookController {

    private final AudioBookService audioBookService;

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

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AudioBookResponse> response = audioBookService.getAll(page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get all audiobooks successfully", response);
    }

    @GetMapping("/recommend")
    public ResponseEntity<ResponseObject> getRecommended(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        UUID userId = getUserIdFromAuthentication();
        PageResponse<AudioBookResponse> response = audioBookService.getByCategoryIds(userId, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get recommended audiobooks successfully", response);
    }

    @GetMapping("/new-release")
    public ResponseEntity<ResponseObject> getNewRelease(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<AudioBookResponse> response = audioBookService.getAllByPublishedYearDesc(page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get new release audiobooks successfully", response);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> getBySearch(@RequestParam String searchTxt,@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size){

        PageResponse<AudioBookResponse> response = audioBookService.getBySearch(searchTxt, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get all audiobooks successfully", response);
    }

    @GetMapping("/search-by-user")
    public ResponseEntity<ResponseObject> getBySearchWithUser(@RequestParam String searchTxt,@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size){
        UUID userId = getUserIdFromAuthentication();
        PageResponse<AudioBookResponse> response = audioBookService.getBySearchWithUser(userId, searchTxt, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get  audiobooks with user successfully", response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseObject> getByCategoryId(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AudioBookResponse> response = audioBookService.getByCategoryId(categoryId, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK,
                "Get audiobooks by category successfully", response);
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseObject> getByUserId(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        UUID userId = getUserIdFromAuthentication();
        PageResponse<AudioBookResponse> response = audioBookService.getByUserId(userId, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK,
                "Get audiobooks by user successfully", response);
    }

    @GetMapping("/{audioBookId}")
    public ResponseEntity<ResponseObject> getByUserId(
            @PathVariable UUID audioBookId) {
        AudioBookResponse response = audioBookService.getById(audioBookId);
        return buildResponse(org.springframework.http.HttpStatus.OK,
                "Get audiobooks by user successfully", response);
    }

    @PostMapping("create")
    public ResponseEntity<ResponseObject> createAudioBook(@Valid @RequestBody AudioBookCreateRequest audioBookCreateRequest) {
        UUID userId = getUserIdFromAuthentication();
        audioBookCreateRequest.setUserId(userId);
        AudioBookCreateRequest createdAudioBook = audioBookService.createAudioBook(audioBookCreateRequest);
        return buildResponse(HttpStatus.OK, "Create audiobook by user successfully", createdAudioBook);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateAudioBook(
            @PathVariable UUID id,
            @Valid @RequestBody AudioBookCreateRequest audioBookCreateRequest) {
        UUID userId = getUserIdFromAuthentication();
        audioBookCreateRequest.setUserId(userId);
        AudioBookCreateRequest updatedAudioBook = audioBookService.updateAudioBook(id, audioBookCreateRequest);
        return buildResponse(HttpStatus.OK, "Create audiobook by user successfully", updatedAudioBook);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteAudioBook(
            @PathVariable UUID id) {
        UUID userId = getUserIdFromAuthentication();
        // Kiểm tra xem audiobook có thuộc về user hiện tại không (nếu cần)
        AudioBookResponse audioBook = audioBookService.getById(id);
        if (!audioBook.getUserId().equals(userId.toString())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this audiobook");
        }

        audioBookService.deleteAudioBook(id);
        return buildResponse(HttpStatus.OK, "Delete audiobook successfully", null);
    }
}