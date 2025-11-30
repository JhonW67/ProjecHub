package com.ProjectHub.service;

import com.ProjectHub.dto.DocumentCreateDTO;
import com.ProjectHub.model.Document;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
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

    public void salvarArquivo(Project projeto, MultipartFile file, User uploader) {
        if (file.isEmpty()) {
            throw new RuntimeException("Arquivo vazio");
        }

        // valida extensão simples
        String originalName = Optional.ofNullable(file.getOriginalFilename()).orElse("arquivo");
        String lower = originalName.toLowerCase();

        if (!(lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") ||
                lower.endsWith(".pdf") || lower.endsWith(".doc") || lower.endsWith(".docx") ||
                lower.endsWith(".mp4") || lower.endsWith(".zip"))) {
            throw new RuntimeException("Tipo de arquivo não permitido");
        }

        try {
            // pasta: uploads/docs/<projectId>/
            Path root = Paths.get("uploads/docs").toAbsolutePath().normalize();
            Path projectDir = root.resolve(projeto.getProjectId().toString());
            Files.createDirectories(projectDir);

            // nome físico do arquivo
            String storedName = UUID.randomUUID() + "-" + originalName;
            Path target = projectDir.resolve(storedName);

            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            Document doc = new Document();
            doc.setProject(projeto);
            doc.setUploader(uploader);
            doc.setFilename(originalName);
            doc.setFilepath(target.toString());
            doc.setType(detectTypeFromExtension(lower));
            doc.setCreatedAt(Timestamp.from(Instant.now()));

            documentRepository.save(doc);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo", e);
        }
    }

    private String detectTypeFromExtension(String lowerName) {
        if (lowerName.endsWith(".pdf")) return "pdf";
        if (lowerName.endsWith(".mp4")) return "video";
        if (lowerName.endsWith(".zip")) return "zip";
        if (lowerName.endsWith(".png") || lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg"))
            return "image";
        if (lowerName.endsWith(".doc") || lowerName.endsWith(".docx")) return "doc";
        return "file";
    }
}

