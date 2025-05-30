package org.example.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "rating", schema = "audio_book")
public class Rating {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @Column(name = "comment", length = 75)
    String comment;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;

    @Column(name = "rating", nullable = false)
    Integer rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "audio_book_id", nullable = false)
    AudioBook audioBook;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}