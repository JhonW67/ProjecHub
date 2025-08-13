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
    @Column(columnDefinition = "varchar(50) not null", unique = true)
    private String role;
    @OneToMany(orphanRemoval = true)// indica que a remoção de um usuário deve remover suas roles associadas
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(
            name = "fk_roles_users"
    )) // Define a chave estrangeira para a tabela de usuários
    private Set<UserModel> users;// Lista de usuários associados a essa função

}
