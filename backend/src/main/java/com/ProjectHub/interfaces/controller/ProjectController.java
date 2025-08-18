package com.ProjectHub.interfaces.controller;

import com.ProjectHub.domain.entity.Project;
import com.ProjectHub.infrastructure.repository.ProjectRepository;
import com.ProjectHub.interfaces.dto.ProjectCreateRequest;
import com.ProjectHub.interfaces.dto.ProjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;

    @GetMapping
    public List<ProjectResponse> list() {
        return projectRepository.findAll().stream().map(this::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> get(@PathVariable UUID id) {
        return projectRepository.findById(id)
                .map(p -> ResponseEntity.ok(toResponse((Project) p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@RequestBody @Valid ProjectCreateRequest req) {
        Project p = Project.builder()
                .name(req.getName())
                .description(req.getDescription())
                .build();
        p = projectRepository.save(p);
        ProjectResponse body = toResponse(p);
        return ResponseEntity.created(URI.create("/projects/" + body.getId())).body(body);
    }

    private ProjectResponse toResponse(Project p) {
        return ProjectResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .build();
    }
}