package org.example.audiobook.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.dto.request.audiobook.AudioBookCreateRequest;
import org.example.audiobook.dto.request.rating.RatingRequest;
import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.PageResponse;
import org.example.audiobook.dto.response.RatingResponse;
import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.Category;
import org.example.audiobook.entity.Rating;
import org.example.audiobook.entity.User;
import org.example.audiobook.exception.ErrorCode;
import org.example.audiobook.exception.custom.AppException;
import org.example.audiobook.mapper.AudioBookMapper;
import org.example.audiobook.mapper.RatingMapper;
import org.example.audiobook.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingService {
    RatingRepository ratingRepository;
    RatingMapper ratingMapper;
    AudioBookRepository audioBookRepository;
    private final UserRepository userRepository;

    public PageResponse<RatingResponse> getAll(int page, int size, UUID audioBookId) {
        // Define pageable
        Pageable pageable = PageRequest.of(page - 1, size);
        // Find
        Page<Rating> ratingPage = ratingRepository.findAll(pageable, audioBookId);
        // Map & Return
        return buildPageResponse(ratingPage);
    }

    private PageResponse<RatingResponse> buildPageResponse(Page<Rating> page) {
        List<RatingResponse> content = page.getContent()
                .stream()
                .map(ratingMapper::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                content
        );
    }

    public String submitRating(RatingRequest request) {
        // New rating
        Rating rating = Rating.builder()
                .comment(request.getComment())
                .rating(request.getRating())
                .audioBook(audioBookRepository.getReferenceById(request.getAudioBookId()))
                .user(userRepository.getReferenceById(request.getUserId()))
                .build();
        // Save & Return
        ratingRepository.save(rating);
        return "Submit success.";
    }
}

