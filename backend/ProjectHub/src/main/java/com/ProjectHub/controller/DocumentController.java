package com.ProjectHub.controller;

import com.ProjectHub.model.Document;
import com.ProjectHub.repository.UserRepository;
import com.ProjectHub.service.DocumentService;
import com.ProjectHub.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Document>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> buscar(@PathVariable UUID id) {
        Document obj = service.buscarPorId(id);
        if (obj == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Document> criar(@RequestBody Document obj) {
        Document salvo = service.salvar(obj);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        Document doc = service.buscarPorId(id);
        if (doc == null || doc.getFilepath() == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path path = Paths.get(doc.getFilepath());
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String filename = doc.getFilename() != null ? doc.getFilename() : "arquivo";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> atualizar(@PathVariable UUID id, @RequestBody Document obj) {
        Document existente = service.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        obj.setDocumentId(id);
        Document atualizado = service.salvar(obj);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}

