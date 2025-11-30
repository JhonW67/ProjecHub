package com.ProjectHub.service.mapping;

import java.util.stream.Collectors;

import com.ProjectHub.dto.*;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.model.Evaluation;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectDetailDTO toDetailDto(Project project, User requester) {
        boolean isAdmin = requester != null && "admin".equalsIgnoreCase(requester.getRole());
        boolean isMember = requester != null && project.getMembers()
                .stream()
                .anyMatch(m -> m.getUser().getUserId().equals(requester.getUserId()));


        ProjectDetailDTO dto = new ProjectDetailDTO();
        dto.setProjectId(project.getProjectId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setGroupName(project.getGroupName());
        dto.setCreatedAt(project.getCreatedAt().toLocalDateTime().toLocalDate());
        dto.setImageUrl(project.getImageUrl());

        // Membros
        dto.setMembers(project.getMembers().stream()
                .map(pm -> new UserDTO(pm.getUser().getUserId(), pm.getUser().getName(), pm.getRole()))
                .collect(Collectors.toList()));

        // Documentos
        dto.setDocuments(project.getDocuments().stream()
                .map(d -> {
                    // DocumentCreateDTO = (UUID id, String name, String type, String url)
                    String urlFinal;
                    if (d.getUrl() != null && !d.getUrl().isBlank()) {
                        urlFinal = d.getUrl(); // É um documento do tipo link externo
                    } else if (d.getFilepath() != null && !d.getFilepath().isBlank()) {
                        urlFinal = "http://localhost:8080/api/documents/" + d.getId() + "/download";
                        // Use APP_URL/config se for em produção
                    } else {
                        urlFinal = null;
                    }
                    return new DocumentCreateDTO(d.getId(), d.getName(), d.getType(), urlFinal);
                })
                .collect(Collectors.toList()));


        // Feedbacks
        dto.setFeedbacks(project.getFeedbacks().stream()
                .map(f -> new FeedbackDTO(
                        f.getId(),
                        f.getAuthor().getName(),
                        f.getComment(),
                        f.getRating()
                ))
                .collect(Collectors.toList()));


        // QR Code
        dto.setQrCodeUrl(project.getQrCodeUrl());

        // Avaliação
        if (isAdmin || isMember) {
            if (project.getEvaluation() != null) {
                Evaluation eval = project.getEvaluation();
                dto.setEvaluation(new EvaluationDTO(
                        eval.getGrade(),
                        eval.getComment(),
                        eval.getProfessor().getName()
                ));
            }

        }
        dto.setCanEdit(isAdmin || isMember);
        return dto;
    }
}
