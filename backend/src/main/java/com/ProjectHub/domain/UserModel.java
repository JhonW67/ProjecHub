package com.ProjectHub.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class UserModel extends IdModel {
    @Column(columnDefinition = "varchar(255) not null")
    private String name;
    @Column(columnDefinition = "varchar(255) not null", unique = true)
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

}
