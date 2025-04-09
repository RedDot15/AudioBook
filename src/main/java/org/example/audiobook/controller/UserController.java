package org.example.audiobook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.UserDTO;
import org.example.audiobook.dto.UserLoginDTO;
import org.example.audiobook.entity.User;
import org.example.audiobook.response.LoginResponse;
import org.example.audiobook.response.RegisterResponse;
import org.example.audiobook.service.user.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> getUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            return ResponseEntity.ok(LoginResponse.builder().
                    token(token).
                    message("Login successful").
                    build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder().message("Login failed")
                            .build()
            );
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            registerResponse.setMessage(errorMessages.toString());
            return ResponseEntity.badRequest().body(registerResponse);
        }
        try {
            User user = userService.createUser(userDTO);
            registerResponse.setMessage("User registered successfully");
            registerResponse.setUser(user);
            return ResponseEntity.ok(registerResponse);
        } catch (Exception e) {
            registerResponse.setMessage("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(registerResponse);
        }
    }
}
