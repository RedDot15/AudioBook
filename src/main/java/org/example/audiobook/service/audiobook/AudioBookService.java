package org.example.audiobook.service.audiobook;

import org.example.audiobook.dto.response.AudioBookResponse;
import org.example.audiobook.dto.response.PageResponse;

import java.util.UUID;

public interface AudioBookService {
    PageResponse<AudioBookResponse> getAll(int page, int size);

    PageResponse<AudioBookResponse> getByCategoryId(UUID categoryId, int page, int size);

    PageResponse<AudioBookResponse> getByUserId(UUID userId, int page, int size);

    AudioBookResponse getById(UUID id);
}
