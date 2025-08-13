package com.ProjectHub.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Table(name = "projects")
@Builder
public class ProjectModel extends IdModel {
    private String name; // nome do projeto
    private String description; // descrição do projeto
    private String bannerUrl; // URL do banner do projeto
    @Enumerated(EnumType.STRING)
    private ProjectStatus status; // status do projeto (ex: "em andamento", "concluído")
    private String startDate; // data de início do projeto
    private String endDate; // data de término do projeto, se aplicável
    private Integer semester; // semestre em que o projeto foi iniciado
    private String course; // curso relacionado ao projeto, se aplicável
    private boolean isActive; // indica se o projeto está ativo ou não
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

}
