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
@Table(name = "book_chapters", schema = "audio_book")
public class BookChapter {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @Column(name = "title", nullable = false, length = 45)
    String title;

    @Column(name = "chapter_number", nullable = false)
    Integer chapterNumber;

    @Column(name = "text_content", nullable = false)
    String textContent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "audio_book_id", nullable = false)
    AudioBook audioBook;

}