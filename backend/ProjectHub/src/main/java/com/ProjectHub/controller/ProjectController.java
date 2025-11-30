package com.ProjectHub.controller;

import com.ProjectHub.dto.DocumentCreateDTO;
import com.ProjectHub.dto.FeedbackCreateDTO;
import com.ProjectHub.dto.ProjectBasicUpdateDTO;
import com.ProjectHub.dto.ProjectDetailDTO;
import com.ProjectHub.model.Feedback;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.UserRepository;
import com.ProjectHub.service.DocumentService;
import com.ProjectHub.service.FeedbackService;
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
    private DocumentService documentService;

    @Autowired
    private FeedbackService feedbackService;

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

    @PutMapping("/{id}/basic")
    public ResponseEntity<ProjectDetailDTO> atualizarBasico(
            @PathVariable UUID id,
            @RequestBody ProjectBasicUpdateDTO req,
            @AuthenticationPrincipal UserDetails userDetails) {

        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        // TODO: validar se requester pode editar
        User requester = null;
        if (userDetails != null) {
            requester = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        }

        projectService.atualizarBasicoEIntegrantes(projeto, req);
        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, requester);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/documents")
    public ResponseEntity<ProjectDetailDTO> adicionarDocumento(
            @PathVariable UUID id,
            @RequestBody DocumentCreateDTO req,
            @AuthenticationPrincipal UserDetails userDetails) {

        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        User requester = null;
        if (userDetails != null) {
            requester = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        }

        // TODO: checar se requester pode editar (membro/admin)
        documentService.adicionarDocumento(projeto, req);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, requester);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{projectId}/documents/{documentId}")
    public ResponseEntity<ProjectDetailDTO> removerDocumento(
            @PathVariable UUID projectId,
            @PathVariable UUID documentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Project projeto = projectService.buscarPorId(projectId);
        if (projeto == null) return ResponseEntity.notFound().build();

        User requester = null;
        if (userDetails != null) {
            requester = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        }

        // checa permissão (membro/admin)
        if (!projectService.podeEditarProjeto(projeto, requester)) {
            return ResponseEntity.status(403).build();
        }

        documentService.removerDocumento(projeto, documentId);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, requester);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/feedbacks")
    public ResponseEntity<ProjectDetailDTO> adicionarFeedback(
            @PathVariable UUID id,
            @RequestBody FeedbackCreateDTO req,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        User requester = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (requester == null) return ResponseEntity.status(401).build();

        feedbackService.adicionarFeedback(projeto, requester, req);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, requester);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{projectId}/feedbacks/{feedbackId}")
    public ResponseEntity<ProjectDetailDTO> removerFeedback(
            @PathVariable UUID projectId,
            @PathVariable UUID feedbackId,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) return ResponseEntity.status(401).build();

        Project projeto = projectService.buscarPorId(projectId);
        if (projeto == null) return ResponseEntity.notFound().build();

        User requester = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (requester == null) return ResponseEntity.status(401).build();

        // regra simples: autor pode apagar o próprio feedback, admin também
        Feedback fb = feedbackService.buscarPorId(feedbackId);
        if (fb == null || !fb.getProject().getProjectId().equals(projectId)) {
            return ResponseEntity.notFound().build();
        }
        boolean isAdmin = "admin".equalsIgnoreCase(requester.getRole());
        boolean isAuthor = fb.getAuthor().getUserId().equals(requester.getUserId());
        if (!isAdmin && !isAuthor) {
            return ResponseEntity.status(403).build();
        }

        feedbackService.deletar(feedbackId);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, requester);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        projectService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}




