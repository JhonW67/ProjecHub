package com.ProjectHub.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Table(name = "projects")
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProjectModel extends IdModel {
    @Column(columnDefinition = "varchar(255) not null")
    private String name; // nome do projeto
    @Column(columnDefinition = "varchar(255) not null")
    private String description; // descrição do projeto
    private String bannerUrl; // URL do banner do projeto
    @Enumerated(EnumType.STRING)
    private ProjectStatus status; // status do projeto (ex: "em andamento", "concluído")
    private String startDate; // data de início do projeto
    private String endDate; // data de término do projeto, se aplicável
    @Column(columnDefinition = "int(2) not null")
    private Integer semester; // semestre em que o projeto foi iniciado
    @Column(columnDefinition = "varchar(255) not null")
    private String course; // curso relacionado ao projeto, se aplicável
    @Column(columnDefinition = "bool not null")
    private boolean isActive; // indica se o projeto está ativo ou não
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    public enum ProjectStatus {
        IN_PROGRESS, COMPLETED, ON_HOLD, CANCELLED
    }

}
