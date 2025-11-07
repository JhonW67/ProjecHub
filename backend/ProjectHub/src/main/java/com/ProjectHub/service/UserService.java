package com.ProjectHub.service;

import com.ProjectHub.model.User;
import com.ProjectHub.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    public User buscarPorId(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User salvar(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return userRepository.save(user);
    }

    public void deletar(UUID id) {
        userRepository.deleteById(id);
    }
}