package org.example.audiobook.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
	// General
	UNCATEGORIZED(HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized error."),
	// User
	USER_DUPLICATE(HttpStatus.CONFLICT, "User already exists."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found."),
	WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "Wrong password."),
	// Authentication
	UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "Unauthenticated error."),
	UNAUTHORIZED(HttpStatus.FORBIDDEN, "You do not have permission to perform this operation."),
	// Audio Book
	AUDIO_BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "Audio book not found."),
    INVALID_CATEGORY_IDS(HttpStatus.FORBIDDEN, "Invalid category ids." ), CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Category not found");

	HttpStatus httpStatus;
	String message;
}
