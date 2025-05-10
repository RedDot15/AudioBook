package org.example.audiobook.dto.request.audiobook;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterRequest {

    @NotBlank(message = "Chapter title is required")
    @Size(max = 45, message = "Chapter title must be less than 45 characters")
    private String title;

    @NotNull(message = "Chapter number is required")
    @Positive(message = "Chapter number must be positive")
    private Integer chapterNumber;

    @NotBlank(message = "Text content is required")
    private String textContent;
}