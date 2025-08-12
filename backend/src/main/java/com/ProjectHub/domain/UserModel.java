package com.ProjectHub.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set
@Data
@Entity
@Table(name = "users")
// essa classe representa o modelo de usuário
// que será persistido no banco de dados
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password_hash;
    @OneToMany
    @JoinColumn(name="user_id")
    private Set<Roles> roles; // pode ser "admin" ou "aluno" ou "professor"
    private String course; // curso do usuário, se aplicável
    private String registration; // matrícula do usuário, se aplicável
    private short  semester; // semestre do usuário, se aplicável
    private boolean isActive; // indica se o usuário está ativo ou não
}
