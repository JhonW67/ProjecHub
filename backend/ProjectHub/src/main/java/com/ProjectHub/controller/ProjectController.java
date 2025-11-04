package com.ProjectHub.controller;

import com.ProjectHub.model.Project;
import com.ProjectHub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> listar() {
        return ResponseEntity.ok(projectService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> buscar(@PathVariable UUID id) {
        Project project = projectService.buscarPorId(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
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