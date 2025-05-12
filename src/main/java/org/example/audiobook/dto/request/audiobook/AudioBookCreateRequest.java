package org.example.audiobook.dto.request.audiobook;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioBookCreateRequest {

    private UUID id;

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title must be less than 50 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author must be less than 50 characters")
    private String author;

    @NotNull(message = "Published year is required")
    @Min(value = 1900, message = "Published year must be after 1900")
    private Integer publishedYear;

    @Size(max = 100, message = "Description must be less than 100 characters")
    private String description;

    @NotBlank(message = "Cover image URL is required")
    @Size(max = 125, message = "Cover image URL must be less than 125 characters")
    private String coverImage;

    @NotNull(message = "Is free status is required")
    private Boolean isFree;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    private Integer duration;

    @Size(max = 1000, message = "Female audio URL must be less than 125 characters")
    private String femaleAudioUrl;

    @Size(max = 1000, message = "Male audio URL must be less than 125 characters")
    private String maleAudioUrl;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    private UUID userId;

    @NotNull(message = "Textcontent are required")
    private String textContent;
}