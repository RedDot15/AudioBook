package org.example.audiobook.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
	UUID id;

	String username;

	String email;

	String role;

	Instant preniumExpiry;

	Boolean preniumStatus;

	Instant createdAt;
}
