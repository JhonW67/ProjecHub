package com.ProjectHub.service;

import com.ProjectHub.dto.EvaluationCreateDTO;
import com.ProjectHub.model.Evaluation;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public Evaluation salvarOuAtualizar(Project projeto, User professor, EvaluationCreateDTO req) {
        Evaluation eval = evaluationRepository.findByProject(projeto).orElse(new Evaluation());
        eval.setProject(projeto);
        eval.setProfessor(professor);
        eval.setGrade(req.getGrade());
        eval.setComment(req.getComment());
        return evaluationRepository.save(eval);
    }

    public Evaluation buscarPorId(UUID id) {
        return null;
    }

    public Evaluation salvar(Evaluation obj) {
        return null;
    }

    public void deletar(UUID id) {
    }

    public List<Evaluation> listarTodos() {
        return null;
    }
}
