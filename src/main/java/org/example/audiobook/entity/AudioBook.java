package org.example.audiobook.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class AudioBook {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @ManyToOne
    User user;

    @ManyToOne
    Category category;

    String title;

    String author;

    String description;

    String coverImage;

    Integer publishedYear;

    Boolean isFree;

    String textContent;

    String maleAudioUrl;

    String femaleAudioUrl;

    Integer duration;
}
