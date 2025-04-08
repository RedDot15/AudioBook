package org.example.audiobook.service.audiobook;

import org.example.audiobook.dto.response.AudioBookResponseDTO;
import org.example.audiobook.dto.response.PageResponse;

import java.util.UUID;

public interface AudioBookService {
    PageResponse<AudioBookResponseDTO> getAll(int page, int size);
    PageResponse<AudioBookResponseDTO> getByCategoryId(UUID categoryId, int page, int size);
    PageResponse<AudioBookResponseDTO> getByUserId(UUID userId, int page, int size);
}
