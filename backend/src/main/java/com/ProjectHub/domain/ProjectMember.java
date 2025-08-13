package com.ProjectHub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "project_members")
@Data
public class ProjectMember extends IdModel {
    @ManyToMany
    private ProjectModel project;
    @ManyToMany
    private UserModel users;
    private String roleInProject; // papel do usuário no projeto, ex: "desenvolvedor", "gerente", etc.
}