package org.example.audiobook.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
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

    @Column(name = "username", length = 50)
    String username;

    @Column(name = "email", nullable = false, length = 50)
    String email;

    @Column(name = "hashed_password", nullable = false)
    String hashedPassword;

    @Column(name = "role", nullable = false, length = 45)
    String role;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "display_name")
    String displayName;

    @Column(name = "prenium_expiry")
    Instant preniumExpiry;

    @Column(name="fcm_token")
    String fcmToken;

    @Column(name = "prenium_status", nullable = false)
    Boolean preniumStatus;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (role == null) role = "USER";
        preniumStatus = false;
        createdAt = Instant.now();
    }
}