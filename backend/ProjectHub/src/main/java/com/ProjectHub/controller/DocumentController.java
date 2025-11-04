package com.ProjectHub.controller;

import com.ProjectHub.model.Document;
import com.ProjectHub.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService service;

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

