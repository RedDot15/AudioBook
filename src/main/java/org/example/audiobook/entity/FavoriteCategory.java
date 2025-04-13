package org.example.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "favorite_category", schema = "audio_book")
public class FavoriteCategory {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "BINARY(16)")
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @Column(name = "category_name", nullable = false, length = 100)
    String categoryName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false, columnDefinition = "BINARY(16)")
    Category category;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;
}