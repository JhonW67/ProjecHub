package com.ProjectHub.controller;

import com.ProjectHub.domain.UserModel;
import com.ProjectHub.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    // Aqui você pode adicionar os métodos para manipular usuários, como criar, atualizar, deletar e listar usuários.

    private final UserRepository userRepository;

    @GetMapping
    public List<UserModel> listarUsuarios(){
        // Metodo para listar todos os usuários
        return userRepository.findAll();
    }

    @PostMapping
    public UserModel criarUsuario(@RequestBody UserModel userModel){
        // Metodo para criar um novo usuário
        return userRepository.save(userModel);
    }
}
