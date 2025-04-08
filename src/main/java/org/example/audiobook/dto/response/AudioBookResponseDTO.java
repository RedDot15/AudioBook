package org.example.audiobook.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AudioBookResponseDTO {
    UUID id;
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
