package com.ProjectHub.controller;

import com.ProjectHub.model.User;
import com.ProjectHub.service.AuthService;
import com.ProjectHub.service.JwtService;
import com.ProjectHub.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = jwtService.login(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok().body("{ \"token\": \"" + token + "\" }");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("{ \"error\": \"" + e.getMessage() + "\" }");
        }
    }
}

