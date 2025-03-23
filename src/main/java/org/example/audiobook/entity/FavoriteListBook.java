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
public class FavoriteListBook {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @ManyToOne
    FavoriteList favoriteList;

    AudioBook audioBook;
}
