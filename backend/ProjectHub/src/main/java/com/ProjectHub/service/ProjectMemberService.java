package com.ProjectHub.service;

import com.ProjectHub.model.ProjectMember;
import com.ProjectHub.repository.ProjectMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectMemberService {

    @Autowired
    private ProjectMemberRepository repository;

    public List<ProjectMember> listarTodos() {
        return repository.findAll();
    }

    public ProjectMember buscarPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public ProjectMember salvar(ProjectMember projectMember) {
        return repository.save(projectMember);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}

