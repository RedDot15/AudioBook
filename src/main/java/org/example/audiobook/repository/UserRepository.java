package org.example.audiobook.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.audiobook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(@Size(max = 50) @NotNull String username);

    Optional<User> findByEmail(@Size(max = 50) @NotNull String email);

    Optional<User> findById(UUID id);

    List<User> findByFcmTokenIsNotNull();
}
