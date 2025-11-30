package com.ProjectHub.service;

import com.ProjectHub.dto.ProjectBasicUpdateDTO;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.ProjectMember;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.UserRepository;
import com.ProjectHub.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Project> listarTodos() {
        return projectRepository.findAll();
    }

    public Project buscarPorId(UUID id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project salvar(Project project) {
        if (project.getCreatedAt() == null) {
            project.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return projectRepository.save(project);
    }

    public void atualizarBasicoEIntegrantes(Project projeto, ProjectBasicUpdateDTO req) {
        projeto.setImageUrl(req.getImageUrl());
        projeto.setGroupName(req.getGroupName());

        if (req.getMemberIds() != null) {
            List<User> novosMembros = userRepository.findAllById(req.getMemberIds());
            // supondo que Project tem List<ProjectMember> members
            List<ProjectMember> lista = novosMembros.stream()
                    .map(u -> new ProjectMember(null, projeto, u, "member", Timestamp.from(Instant.now())))
                    .collect(Collectors.toList());
            projeto.setMembers(lista);
        }

        projectRepository.save(projeto);
    }

    public boolean podeEditarProjeto(Project projeto, User requester) {
        if (requester == null) return false;
        boolean isAdmin = "admin".equalsIgnoreCase(requester.getRole());
        boolean isMember = projeto.getMembers() != null &&
                projeto.getMembers().stream()
                        .anyMatch(pm -> pm.getUser().getUserId().equals(requester.getUserId()));
        return isAdmin || isMember;
    }


    public void deletar(UUID id) {
        projectRepository.deleteById(id);
    }

}