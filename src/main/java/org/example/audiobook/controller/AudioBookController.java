// AudioBookController.java
package org.example.audiobook.controller;

import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.AudioBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.audiobook.helper.ResponseBuilder.buildResponse;

@RestController
@RequestMapping("/api/audio-book")
@RequiredArgsConstructor
public class AudioBookController {

    private final AudioBookService audioBookService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AudioBookResponse> response = audioBookService.getAll(page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get all audiobooks successfully", response);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseObject> getBySearch(@RequestParam String searchTxt,@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int size){

        PageResponse<AudioBookResponse> response = audioBookService.getBySearch(searchTxt, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get all audiobooks successfully", response);
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getByUserId(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
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


}