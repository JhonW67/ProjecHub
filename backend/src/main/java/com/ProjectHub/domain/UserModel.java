package com.ProjectHub.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
// essa classe representa o modelo de usuário
// que será persistido no banco de dados
public class UserModel extends IdModel {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password_hash;
    @OneToMany
    @JoinColumn(name="user_id")
    private Set<Roles> roles; // pode ser "admin" ou "aluno" ou "professor"
    private String course; // curso do usuário, se aplicável
    private String registration; // matrícula do usuário, se aplicável
    private short  semester; // semestre do usuário, se aplicável
    private boolean isActive; // indica se o usuário está ativo ou não
    private String avatarUrl;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
