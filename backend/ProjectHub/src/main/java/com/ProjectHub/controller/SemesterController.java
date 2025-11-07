package com.ProjectHub.controller;

import com.ProjectHub.model.Semester;
import com.ProjectHub.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    @GetMapping
    public ResponseEntity<List<Semester>> listar() {
        return ResponseEntity.ok(semesterService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Semester> buscar(@PathVariable UUID id) {
        Semester semester = semesterService.buscarPorId(id);
        if (semester == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(semester);
    }

    @PostMapping
    public ResponseEntity<Semester> criar(@RequestBody Semester semester) {
        Semester salvo = semesterService.salvar(semester);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Semester> atualizar(@PathVariable UUID id, @RequestBody Semester semester) {
        Semester existente = semesterService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        semester.setSemesterId(id);
        Semester atualizado = semesterService.salvar(semester);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        semesterService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}