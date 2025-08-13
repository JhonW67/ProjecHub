package com.ProjectHub.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "roles") // Define o nome da tabela no banco de dados
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// O ID é gerado automaticamente pelo banco de dados
    private Long id;
    private String role;
    @OneToMany
    @JoinColumn(name = "role_id")
    private Set<UserModel> users;// Lista de usuários associados a essa função

}
