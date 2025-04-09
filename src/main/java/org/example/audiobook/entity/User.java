package org.example.audiobook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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
public class User implements  UserDetails {
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

    @Column(name = "role", nullable = false, length = 45)
    String role;

    @Column(name = "prenium_expiry")
    Instant preniumExpiry;

    @Column(name = "prenium_status", nullable = false)
    Boolean preniumStatus;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "created_at", nullable = false)
    Instant createdAt;

    @PrePersist
    protected void onCreate() {
        if (role == null) role = "USER";
        preniumStatus = false;
        createdAt = Instant.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return  true;
    }
}