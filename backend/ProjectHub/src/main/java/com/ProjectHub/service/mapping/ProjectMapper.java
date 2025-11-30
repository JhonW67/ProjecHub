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
                .map(d -> new DocumentCreateDTO(d.getId(), d.getName(), d.getType(), d.getUrl()))
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
            Evaluation eval = project.getEvaluation();
            if (eval != null) {
                dto.setEvaluation(new EvaluationDTO(
                        eval.getComment(),
                        eval.getGrade(),
                        eval.getProfessor().getName()
                ));
            }
        }
        dto.setCanEdit(isAdmin || isMember);
        return dto;
    }
}
