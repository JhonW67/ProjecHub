package com.ProjectHub.dto;

import java.util.List;
import java.util.UUID;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String registration;       // matrícula
    private String role;               // aluno/professor/admin
    private UUID courseId;             // ID do curso selecionado
    private UUID semesterId;           // ID do semestre selecionado
    private List<UUID> courseIds;      // professor
    private List<String> subjects;     // professor
}
