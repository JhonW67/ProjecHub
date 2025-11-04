package com.ProjectHub.controller;

import com.ProjectHub.model.ProjectMember;
import com.ProjectHub.service.ProjectMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/project-members")
public class ProjectMemberController {

    @Autowired
    private ProjectMemberService service;

    @GetMapping
    public ResponseEntity<List<ProjectMember>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectMember> buscar(@PathVariable UUID id) {
        ProjectMember pm = service.buscarPorId(id);
        if (pm == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pm);
    }

    @PostMapping
    public ResponseEntity<ProjectMember> criar(@RequestBody ProjectMember pm) {
        ProjectMember salvo = service.salvar(pm);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectMember> atualizar(@PathVariable UUID id, @RequestBody ProjectMember pm) {
        ProjectMember existente = service.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        pm.setId(id);
        ProjectMember atualizado = service.salvar(pm);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
