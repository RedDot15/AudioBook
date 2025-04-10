package org.example.audiobook.dto.request.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
	@NotBlank(message = "email is required.")
	String email;

	@NotBlank(message = "Password is required.")
	String password;
}
