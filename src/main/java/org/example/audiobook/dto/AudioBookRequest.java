package org.example.audiobook.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioBookRequest {

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

    @NotBlank(message = "Female audio URL is required")
    @Size(max = 125, message = "Female audio URL must be less than 125 characters")
    private String femaleAudioUrl;

    @NotBlank(message = "Male audio URL is required")
    @Size(max = 125, message = "Male audio URL must be less than 125 characters")
    private String maleAudioUrl;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @NotNull(message = "User ID is required")
    private UUID userId;
}