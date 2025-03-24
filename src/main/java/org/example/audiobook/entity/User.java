package org.example.audiobook.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users", schema = "audio_book")
public class User {
    @Id
    @Column(name = "id", nullable = false, length = 16)
    @GeneratedValue
    @UuidGenerator
    UUID id;

    @Column(name = "username", nullable = false, length = 50)
    String username;

    @Column(name = "email", nullable = false, length = 50)
    String email;

    @Column(name = "hashed_password", nullable = false)
    String hashedPassword;

    @Column(name = "prenium_expiry")
    Instant preniumExpiry;

    @Column(name = "prenium_status", nullable = false)
    Boolean preniumStatus;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;

    @PrePersist
    protected void onCreate() {
        preniumStatus = false;
        createdAt = Instant.now();
    }
}