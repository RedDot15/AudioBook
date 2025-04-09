package org.example.audiobook.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserChangeInfoRequest {
	@NotNull(message = "Id is required.")
	UUID id;

	@NotBlank(message = "Email is required.")
	@Email(message = "Email must be a valid email address.")
	String email;

}
