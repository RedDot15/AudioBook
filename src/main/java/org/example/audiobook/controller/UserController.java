package org.example.audiobook.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.dto.request.user.*;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.audiobook.helper.ResponseBuilder.buildResponse;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/user")
public class UserController {
	UserService userService;

	@GetMapping("/list/all")
	public ResponseEntity<ResponseObject> showAll() {
		// Fetch & Return all users
		return buildResponse(HttpStatus.OK, "All users fetch successfully.", userService.getAll());
	}

	@GetMapping("/my-info/get")
	public ResponseEntity<ResponseObject> showMyInfo() {
		// Fetch & Return all users
		return buildResponse(HttpStatus.OK, "My information fetch successfully.", userService.getMyInfo());
	}

	@PostMapping("/add")
	public ResponseEntity<ResponseObject> add(
			@Valid @RequestBody UserCreateRequest userCreateRequest) {
		// Create & Return user
		return buildResponse(HttpStatus.OK, "Created new user successfully.", userService.add(userCreateRequest));
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseObject> update(
			@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
		// Update & Return
		return buildResponse(HttpStatus.OK, "Updated user successfully.", userService.update(userUpdateRequest));
	}

	@DeleteMapping(value = "/{userId}/delete")
	public ResponseEntity<ResponseObject> delete(@PathVariable UUID userId) {
		// Delete & Return id
		return buildResponse(HttpStatus.OK, "Deleted user successfully.", userService.delete(userId));
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseObject> registerUser(
			@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
		// Register & Return new user
		return buildResponse(
				HttpStatus.CREATED, "Registered new user successfully.", userService.register(userRegisterRequest));
	}

	@PutMapping("/update-information")
	public ResponseEntity<ResponseObject> updateInformation(
			@Valid @RequestBody UserChangeInfoRequest userChangeInfoRequest) {
		// Update & Return user
		return buildResponse(HttpStatus.OK, "Updated user successfully.", userService.updateInformation(userChangeInfoRequest));
	}

	@PutMapping("/change-password")
	public ResponseEntity<ResponseObject> changePassword(
			@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
		// Update & Return user
		return buildResponse(HttpStatus.OK, "Changed password successfully.", userService.changePassword(userChangePasswordRequest));
	}
}
