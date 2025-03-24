package org.example.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "audio_books", schema = "audio_book")
public class AudioBook {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @Column(name = "title", nullable = false, length = 50)
    String title;

    @Column(name = "author", nullable = false, length = 50)
    String author;

    @Column(name = "published_year", nullable = false)
    Integer publishedYear;

    @Column(name = "description", length = 100)
    String description;

    @Column(name = "cover_image", nullable = false, length = 125)
    String coverImage;

    @Column(name = "is_free", nullable = false)
    Boolean isFree;

    @Column(name = "duration", nullable = false)
    Integer duration;

    @Column(name = "female_audio_url", nullable = false, length = 125)
    String femaleAudioUrl;

    @Column(name = "male_audio_url", nullable = false, length = 125)
    String maleAudioUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

}