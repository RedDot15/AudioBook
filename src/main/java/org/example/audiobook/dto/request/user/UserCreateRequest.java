package org.example.audiobook.dto.request.user;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.validation.annotation.Match;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Match(
		fields = {"password", "confirmPassword"},
		message = "These fields {fields} must match.")
public class UserCreateRequest {
	@NotBlank(message = "Username is required.")
	String username;

	@NotBlank(message = "Email is required.")
	@Email(message = "Email must be a valid email address.")
	String email;

	@NotBlank(message = "Password is required.")
	String password;

	@NotBlank(message = "Confirm password is required.")
	String confirmPassword;

	@NotBlank(message = "Roles is required.")
	String role;
}
