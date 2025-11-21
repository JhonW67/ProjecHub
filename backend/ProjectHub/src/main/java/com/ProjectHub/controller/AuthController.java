package com.ProjectHub.controller;

import com.ProjectHub.dto.LoginResponse;
import com.ProjectHub.dto.UserDTO;
import com.ProjectHub.model.User;
import com.ProjectHub.service.AuthService;
import com.ProjectHub.service.JwtService;
import com.ProjectHub.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            User user = authService.findByEmail(loginRequest.getEmail());
            if (user == null || !new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(401).body("{ \"error\": \"Email ou senha inválidos.\" }");
            }
            String token = jwtService.login(user.getEmail(), loginRequest.getPassword());
            UserDTO userDTO = new UserDTO(user);

            // Retorna objeto, não string (JSON automático pelo Spring)
            return ResponseEntity.ok(new LoginResponse(token, userDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("{ \"error\": \"" + e.getMessage() + "\" }");
        }
    }
}

