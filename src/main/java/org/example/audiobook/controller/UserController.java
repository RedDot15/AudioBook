package org.example.audiobook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    @PostMapping("/login")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok("Hello World");
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser() {
        return ResponseEntity.ok("Register User");
    }
}
