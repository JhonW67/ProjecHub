package com.ProjectHub.service;

import com.ProjectHub.dto.FeedbackCreateDTO;
import com.ProjectHub.model.Feedback;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> listarPorProjeto(Project project) {
        return feedbackRepository.findByProject(project);
    }

    public Feedback buscarPorId(UUID id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback salvar(Feedback fb) {
        if (fb.getCreatedAt() == null) {
            fb.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return feedbackRepository.save(fb);
    }

    public void deletar(UUID id) {
        feedbackRepository.deleteById(id);
    }

    public void adicionarFeedback(Project projeto, User autor, FeedbackCreateDTO req) {
        Feedback fb = new Feedback();
        fb.setProject(projeto);
        fb.setAuthor(autor);
        fb.setComment(req.getComment());
        fb.setRating(req.getRating());
        fb.setCreatedAt(Timestamp.from(Instant.now()));
        feedbackRepository.save(fb);
    }
}
