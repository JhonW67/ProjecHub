package com.ProjectHub.service;

import com.ProjectHub.dto.DocumentCreateDTO;
import com.ProjectHub.model.Document;
import com.ProjectHub.model.Project;
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
    private DocumentRepository documentRepository;

    public List<Document> listarTodos() {
        return documentRepository.findAll();
    }

    public Document buscarPorId(UUID id) {
        return documentRepository.findById(id).orElse(null);
    }


    public void adicionarDocumento(Project projeto, DocumentCreateDTO req) {
        Document doc = new Document();
        doc.setProject(projeto);
        doc.setName(req.getName());
        doc.setType(req.getType());
        doc.setUrl(req.getUrl());
        documentRepository.save(doc);
    }

    public void removerDocumento(Project projeto, UUID documentId) {
        Document doc = documentRepository.findById(documentId).orElse(null);
        if (doc == null) return;
        if (!doc.getProject().getProjectId().equals(projeto.getProjectId())) {
            return; // segurança extra: doc não pertence a este projeto
        }
        documentRepository.delete(doc);
    }


    public Document salvar(Document doc) {
        if (doc.getCreatedAt() == null) {
            doc.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return documentRepository.save(doc);
    }

    public void deletar(UUID id) {
        documentRepository.deleteById(id);
    }
}

