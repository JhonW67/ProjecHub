package com.ProjectHub.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ProjectHub.application.security.JwtUtil;
import com.ProjectHub.domain.entity.User;
import com.ProjectHub.infrastructure.repository.UserRepository;
import com.ProjectHub.interfaces.dto.AuthResult;
import com.ProjectHub.interfaces.dto.LoginRequest;
import com.ProjectHub.interfaces.dto.RegisterRequest;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwt;

    public void register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        User u = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password_hash(passwordEncoder.encode(req.getPassword()))
                .build();
        userRepository.save(u);
    }

    public AuthResult login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        String token = jwt.generateToken(
                user.getId().toString(),
                Map.of("email", user.getEmail(), "name", user.getName())
        );
        long maxAge = 86400L; // 1 dia
        return new AuthResult(token, maxAge);
    }
}
