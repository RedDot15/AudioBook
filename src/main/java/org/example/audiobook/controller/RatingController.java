package org.example.audiobook.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.dto.request.rating.RatingRequest;
import org.example.audiobook.entity.Category;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.repository.CategoryRepository;
import org.example.audiobook.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.audiobook.helper.ResponseBuilder.buildResponse;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/rating")
public class RatingController {

    RatingService ratingService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID audioBookId) {
        // Fetch & Return
        return buildResponse(HttpStatus.OK, "Ratings fetch success.",
                ratingService.getAll(page, size, audioBookId));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> submitRating(
            @RequestBody RatingRequest request) {
        // Fetch & Return
        return buildResponse(HttpStatus.OK, "Rating submitted success.",
                ratingService.submitRating(request));
    }
}