package org.example.audiobook.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.dto.request.authentication.AuthenticationRequest;
import org.example.audiobook.dto.request.authentication.RefreshRequest;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.authentication.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.example.audiobook.helper.ResponseBuilder.buildResponse;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	AuthenticationService authenticationService;

	@PostMapping("/token/get")
	public ResponseEntity<ResponseObject> authenticate(@Valid @RequestBody AuthenticationRequest request) {
		return buildResponse(HttpStatus.OK, "Authenticate successfully.", authenticationService.authenticate(request));
	}

	@PostMapping("/token/refresh")
	public ResponseEntity<ResponseObject> refreshToken(@Valid @RequestBody RefreshRequest request) {
		return buildResponse(HttpStatus.OK, "Authenticate successfully.", authenticationService.refresh(request));
	}

	@PostMapping("/my-token/invalidate")
	public ResponseEntity<ResponseObject> logout() {
		authenticationService.logout();
		return buildResponse(HttpStatus.OK, "Log out successfully.", null);
	}
}
