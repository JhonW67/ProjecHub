package com.ProjectHub.service;

import com.ProjectHub.dto.EvaluationCreateDTO;
import com.ProjectHub.model.Evaluation;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        eval.setGrade(req.getGrade());      // agora grade é Integer nos dois lados
        eval.setComment(req.getComment());
        return evaluationRepository.save(eval);
    }


}
