package com.ProjectHub.domain;


public enum ProjectStatus {
    ONGOING("Em Andamento"),
    COMPLETED("Concluído"),
    ON_HOLD("Em Espera"),
    CANCELLED("Cancelado");

    private final String description;

    ProjectStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

}