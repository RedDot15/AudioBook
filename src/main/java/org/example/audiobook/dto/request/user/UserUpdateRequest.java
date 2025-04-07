package org.example.audiobook.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.validation.annotation.AllOrNone;
import org.example.audiobook.validation.annotation.Match;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllOrNone(
		fields = {"password", "confirmPassword"},
		message = "These fields {fields} must be all null or all not null.")
@Match(
		fields = {"password", "confirmPassword"},
		message = "These fields {fields} must match.")
public class UserUpdateRequest {
	@NotNull(message = "Id is required.")
	UUID id;

	@NotBlank(message = "Email is required.")
	@Email(message = "Email must be a valid email address.")
	String email;

	String password;

	String confirmPassword;

	@NotBlank(message = "Roles is required.")
	String role;
}
