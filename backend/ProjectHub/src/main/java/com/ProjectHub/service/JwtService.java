package com.ProjectHub.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String jwtSecret = "sua-chave-secreta"; // Defina via variável ambiente/config segura

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciais inválidas.");
        }
        // Gera o token JWT
        return JWT.create()
                .withSubject(user.getUserId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}

