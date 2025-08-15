package com.ProjectHub.interfaces.controller;

import com.ProjectHub.domain.Entity.Roles;
import com.ProjectHub.domain.Entity.User;
import com.ProjectHub.interfaces.repository.UserRepository;
import com.ProjectHub.interfaces.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ProjectHub.interfaces.dto.UserCreateRequest;
import com.ProjectHub.interfaces.dto.UserResponse;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    // Aqui você pode adicionar os métodos para manipular usuários, como criar, atualizar, deletar e listar usuários.

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping
    public List<UserResponse> listarUsuarios(){
        // Metodo para listar todos os usuários
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserCreateRequest req) {
        // valida email único (simples p/ o teste)
        if (userRepository.existsById(req.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Roles role = null;
        if (req.getRoleId() != null) {
            role = roleRepository.findById(req.getRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Role não encontrada"));
        }

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password_hash(req.getPassword()) // DEPOIS trocamos por BCrypt
                .roles(role)
                .build();

        user = userRepository.save(user);

        UserResponse body = toResponse(user);
        return ResponseEntity
                .created(URI.create("/users/" + body.getId()))
                .body(body);
    }

    private UserResponse toResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .email(u.getEmail())
                .role(u.getRole() != null ? u.getRole() : null)
                .build();
    }

}
