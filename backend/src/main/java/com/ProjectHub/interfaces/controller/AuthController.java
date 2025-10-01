package com.ProjectHub.interfaces.controller;

import com.ProjectHub.application.services.AuthServices;
import com.ProjectHub.interfaces.dto.AuthResult;
import com.ProjectHub.interfaces.dto.LoginRequest;
import com.ProjectHub.interfaces.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServices authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // DoD: 201
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        AuthResult result = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("jwt", result.token())
                .httpOnly(true)
                .secure(false)    // DEV. Em produção: true (HTTPS)
                .sameSite("Lax")
                .path("/")
                .maxAge(result.maxAgeSeconds())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()) // DoD: Set-Cookie
                .build();
    }
}
