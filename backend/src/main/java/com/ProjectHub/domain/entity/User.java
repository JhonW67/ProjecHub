package com.ProjectHub.domain.entity;

import com.ProjectHub.domain.IdModel;
import com.ProjectHub.interfaces.dto.LoginRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "users_email")
}, indexes ={
        @Index(name = "users_name_idx", columnList = "name"),
        @Index(name = "users_email_idx", columnList = "email")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
// essa classe representa o modelo de usuário
// que será persistido no banco de dados
public class User extends IdModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password_hash;

    @ManyToOne
    @JoinColumn(name="roles_id", referencedColumnName = "id", foreignKey = @ForeignKey(
            name = "fk_users_roles"
    ))
    private Roles roles; // pode ser "admin" ou "aluno" ou "professor"

    @OneToMany
    @JoinColumn(name = "project_members_id", referencedColumnName = "id", foreignKey = @ForeignKey(
            name = "fk_users_project_members"
    ))
    private Set<ProjectMember> projectMembers; // lista de projetos em que o usuário está envolvido

    private String course; // curso do usuário, se aplicável

    private String registration; // matrícula do usuário, se aplicável

    private short  semester; // semestre do usuário, se aplicável

    private boolean isActive; // indica se o usuário está ativo ou não

    private String avatarUrl;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public String getRole() {
        if (roles != null) {
            return roles.getName();
        }
        return null; // ou lançar uma exceção, dependendo do caso de uso
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password_hash);
    }

    public String getPassword() {
        return this.password_hash;
    }
}
