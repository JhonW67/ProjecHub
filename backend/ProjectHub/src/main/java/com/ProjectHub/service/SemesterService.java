package com.ProjectHub.service;

import com.ProjectHub.model.Semester;
import com.ProjectHub.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    public List<Semester> listarTodos() {
        return semesterRepository.findAll();
    }

    public Semester buscarPorId(UUID id) {
        return semesterRepository.findById(id).orElse(null);
    }

    public Semester salvar(Semester semester) {
        return semesterRepository.save(semester);
    }

    public void deletar(UUID id) {
        semesterRepository.deleteById(id);
    }
}