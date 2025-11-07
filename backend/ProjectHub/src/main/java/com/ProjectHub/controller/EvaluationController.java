package com.ProjectHub.controller;

import com.ProjectHub.model.Evaluation;
import com.ProjectHub.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService service;

    @GetMapping
    public ResponseEntity<List<Evaluation>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> buscar(@PathVariable UUID id) {
        Evaluation obj = service.buscarPorId(id);
        if (obj == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Evaluation> criar(@RequestBody Evaluation obj) {
        Evaluation salvo = service.salvar(obj);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluation> atualizar(@PathVariable UUID id, @RequestBody Evaluation obj) {
        Evaluation existente = service.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        obj.setEvaluationId(id);
        Evaluation atualizado = service.salvar(obj);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
