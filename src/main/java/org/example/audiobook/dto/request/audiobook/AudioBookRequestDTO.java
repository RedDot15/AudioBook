package org.example.audiobook.dto.request.audiobook;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioBookRequestDTO {
    String title;
    String author;
    Integer publishedYear;
    String description;
    String coverImage;
    Boolean isFree;
    Integer duration;
    String femaleAudioUrl;
    String maleAudioUrl;
    UUID categoryId;
    UUID userId;
}
