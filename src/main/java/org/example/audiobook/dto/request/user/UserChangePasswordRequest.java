package org.example.audiobook.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.validation.annotation.Match;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Match(
		fields = {"password", "confirmPassword"},
		message = "These fields {fields} must match.")
public class UserChangePasswordRequest {
	@NotNull(message = "Id is required.")
	UUID id;

	@NotBlank(message = "Old password is required.")
	String oldPassword;

	@NotBlank(message = "Password is required.")
	String password;

	@NotBlank(message = "Confirm Password is required.")
	String confirmPassword;


}
