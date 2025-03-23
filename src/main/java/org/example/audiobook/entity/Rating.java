package org.example.audiobook.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class Rating {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @ManyToOne
    User user;

    @ManyToOne
    AudioBook audioBook;

    Integer rating;

    String comment;

    LocalDateTime createdAt;
}
