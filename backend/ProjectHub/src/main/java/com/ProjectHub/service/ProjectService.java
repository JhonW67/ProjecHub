package com.ProjectHub.service;

import com.ProjectHub.model.Project;
import com.ProjectHub.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

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

    public void deletar(UUID id) {
        projectRepository.deleteById(id);
    }

}