package org.example.localproblemsolver.controller;


import jakarta.validation.Valid;

import org.example.localproblemsolver.Service.AuthService;
import org.example.localproblemsolver.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

         RegisterResponse registerResponse = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerResponse
                );
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request ) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/test")
    public boolean test() {
        return true;
    }
}
