package com.ProjectHub.domain.Entity;

import jakarta.persistence.*;

import lombok.Data;

import javax.swing.*;
import java.util.Set;

@Data
@Entity
@Table(name = "roles") // Define o nome da tabela no banco de dados
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// O ID é gerado automaticamente pelo banco de dados
    private Long id;
    @Column(columnDefinition = "varchar(50) not null", unique = true)
    private String roles;
    @OneToMany(orphanRemoval = true)// indica que a remoção de um usuário deve remover suas roles associadas
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(
            name = "fk_roles_users"
    )) // Define a chave estrangeira para a tabela de usuários
    private Set<User> users;// Lista de usuários associados a essa função

    public String getName() {
        if (roles != null) {
            return roles;
        }
        return null; // ou lançar uma exceção, dependendo do caso de uso
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum Values{
        ADMIN(1L),
        STUDENT(2L),
        TEACHER(3L);

        long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }

        public long getRoleId() {
            return roleId;
        }
    }
}
