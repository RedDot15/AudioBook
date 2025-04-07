// AudioBookController.java
package org.example.audiobook.controller;

import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.response.AudioBookResponseDTO;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.audiobook.AudioBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.audiobook.helper.ResponseBuilder.buildResponse;

@RestController
@RequestMapping("/api/audiobooks")
@RequiredArgsConstructor
public class AudioBookController {

    private final AudioBookService audioBookService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AudioBookResponseDTO> response = audioBookService.getAll(page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK, "Get all audiobooks successfully", response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseObject> getByCategoryId(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AudioBookResponseDTO> response = audioBookService.getByCategoryId(categoryId, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK,
                "Get audiobooks by category successfully", response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseObject> getByUserId(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<AudioBookResponseDTO> response = audioBookService.getByUserId(userId, page, size);
        return buildResponse(org.springframework.http.HttpStatus.OK,
                "Get audiobooks by user successfully", response);
    }
}