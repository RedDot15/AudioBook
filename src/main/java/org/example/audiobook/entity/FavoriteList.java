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
public class FavoriteList {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @ManyToOne
    User user;

    String name;
}
