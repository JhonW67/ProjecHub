package com.ProjectHub.controller;

import com.ProjectHub.dto.*;
import com.ProjectHub.model.Feedback;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.UserRepository;
import com.ProjectHub.service.DocumentService;
import com.ProjectHub.service.EvaluationService;
import com.ProjectHub.service.FeedbackService;
import com.ProjectHub.service.ProjectService;
import com.ProjectHub.service.mapping.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping
    public ResponseEntity<List<ProjectListDTO>> listar() {
        List<Project> projects = projectService.listarTodos();
        List<ProjectListDTO> dtos = projects.stream().map(p -> {
            ProjectListDTO dto = new ProjectListDTO();
            dto.setProjectId(p.getProjectId());
            dto.setTitle(p.getTitle());
            dto.setDescription(p.getDescription());
            dto.setStatus(p.getStatus());
            dto.setImageUrl(p.getImageUrl());
            dto.setGroupName(p.getGroupName());
            dto.setCreatedAt(p.getCreatedAt() != null ? p.getCreatedAt().toString() : null);
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
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
    public ResponseEntity<Project> criar(@RequestBody Project project, @AuthenticationPrincipal UserDetails userDetails) {
        User creator = null;
        if (userDetails != null) {
            creator = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        }
        Project salvo = projectService.salvar(project, creator);
        return ResponseEntity.ok(salvo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Project> atualizar(@PathVariable UUID id, @RequestBody Project project, User creator) {
        Project existente = projectService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        project.setProjectId(id);
        Project atualizado = projectService.salvar(project, creator);
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

    @PostMapping("/{id}/banner")
    public ResponseEntity<ProjectDetailDTO> uploadBanner(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {

        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        projectService.salvarBanner(projeto, file);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, null);
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

    @PostMapping("/{id}/documents/upload")
    public ResponseEntity<ProjectDetailDTO> uploadDocument(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {

        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        // sem segurança de usuário por enquanto
        documentService.salvarArquivo(projeto, file, null);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, null);
        return ResponseEntity.ok(dto);
    }



    @DeleteMapping("/{projectId}/documents/{documentId}")
    public ResponseEntity<ProjectDetailDTO> removerDocumento(
            @PathVariable UUID projectId,
            @PathVariable UUID documentId) {

        Project projeto = projectService.buscarPorId(projectId);
        if (projeto == null) return ResponseEntity.notFound().build();

        documentService.removerDocumento(projeto, documentId);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, null);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/feedbacks-debug")
    public ResponseEntity<String> feedbackDebug() {
        System.out.println("ENTROU NO FEEDBACK-DEBUG");
        return ResponseEntity.ok("OK-FEEDBACK-DEBUG");
    }

    @PostMapping("/{id}/feedbacks")
    public ResponseEntity<ProjectDetailDTO> adicionarFeedback(
            @PathVariable UUID id,
            @RequestBody FeedbackCreateDTO req) {

        System.out.println("ENTROU NO FEEDBACK");

        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        User autor = null;
        if (req.getUserId() != null) {
            autor = userRepository.findById(req.getUserId()).orElse(null);
        }

        // se não achou autor, pode retornar 400 ou seguir com null se quiser permitir anônimo
        if (autor == null) {
            return ResponseEntity.badRequest().build();
        }

        feedbackService.adicionarFeedback(projeto, autor, req);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, autor);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{projectId}/feedbacks/{feedbackId}")
    public ResponseEntity<ProjectDetailDTO> removerFeedback(
            @PathVariable UUID projectId,
            @PathVariable UUID feedbackId) {

        Project projeto = projectService.buscarPorId(projectId);
        if (projeto == null) return ResponseEntity.notFound().build();

        Feedback fb = feedbackService.buscarPorId(feedbackId);
        if (fb == null || !fb.getProject().getProjectId().equals(projectId)) {
            return ResponseEntity.notFound().build();
        }

        feedbackService.deletar(feedbackId);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, null);
        return ResponseEntity.ok(dto);
    }



    @PostMapping("/{id}/qrcode")
    public ResponseEntity<ProjectDetailDTO> gerarQrCode(@PathVariable UUID id) {
        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        // base do front – depois pode mover para config
        String frontBaseUrl = "http://localhost:5173";

        projectService.gerarQrCode(projeto, frontBaseUrl);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, null);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/evaluation")
    public ResponseEntity<ProjectDetailDTO> salvarAvaliacao(
            @PathVariable UUID id,
            @RequestBody EvaluationCreateDTO req,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) return ResponseEntity.status(401).build();

        Project projeto = projectService.buscarPorId(id);
        if (projeto == null) return ResponseEntity.notFound().build();

        User professor = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (professor == null) return ResponseEntity.status(401).build();

        // regra simples: só professor pode avaliar
        if (!"teacher".equalsIgnoreCase(professor.getRole()) &&
                !"admin".equalsIgnoreCase(professor.getRole())) {
            return ResponseEntity.status(403).build();
        }

        evaluationService.salvarOuAtualizar(projeto, professor, req);

        ProjectDetailDTO dto = projectMapper.toDetailDto(projeto, professor);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        projectService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}




