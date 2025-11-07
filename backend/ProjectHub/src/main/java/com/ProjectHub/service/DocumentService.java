package com.ProjectHub.service;

import com.ProjectHub.model.Document;
import com.ProjectHub.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository repository;

    public List<Document> listarTodos() {
        return repository.findAll();
    }

    public Document buscarPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Document salvar(Document doc) {
        if (doc.getCreatedAt() == null) {
            doc.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return repository.save(doc);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}

