package com.ProjectHub.controller;

import com.ProjectHub.model.User;
import com.ProjectHub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listar() {
        return ResponseEntity.ok(userService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscar(@PathVariable UUID id) {
        User user = userService.buscarPorId(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> criar(@RequestBody User user) {
        User salvo = userService.salvar(user);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> atualizar(@PathVariable UUID id, @RequestBody User user) {
        User existente = userService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUserId(id);
        User atualizado = userService.salvar(user);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}