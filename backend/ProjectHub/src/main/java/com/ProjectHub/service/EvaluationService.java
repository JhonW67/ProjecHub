package com.ProjectHub.service;

import com.ProjectHub.model.Evaluation;
import com.ProjectHub.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository repository;

    public List<Evaluation> listarTodos() {
        return repository.findAll();
    }

    public Evaluation buscarPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Evaluation salvar(Evaluation evaluation) {
        if (evaluation.getCreatedAt() == null) {
            evaluation.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return repository.save(evaluation);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}

