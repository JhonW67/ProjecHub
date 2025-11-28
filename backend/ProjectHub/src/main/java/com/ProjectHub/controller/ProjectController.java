package com.ProjectHub.controller;

import com.ProjectHub.dto.ProjectDetailDTO;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.UserRepository;
import com.ProjectHub.service.ProjectService;
import com.ProjectHub.service.mapping.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @GetMapping
    public ResponseEntity<List<Project>> listar() {
        return ResponseEntity.ok(projectService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailDTO> getProjectDetails(@PathVariable UUID id) {
        Project project = projectService.buscarPorId(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        // visitante: passa null para o mapper
        ProjectDetailDTO dto = projectMapper.toDetailDto(project, null);
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<Project> criar(@RequestBody Project project) {
        Project salvo = projectService.salvar(project);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> atualizar(@PathVariable UUID id, @RequestBody Project project) {
        Project existente = projectService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        project.setProjectId(id);
        Project atualizado = projectService.salvar(project);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        projectService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}




