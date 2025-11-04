package com.ProjectHub.controller;

import com.ProjectHub.model.Course;
import com.ProjectHub.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> listar() {
        return ResponseEntity.ok(courseService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> buscar(@PathVariable UUID id) {
        Course course = courseService.buscarPorId(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> criar(@RequestBody Course course) {
        Course salvo = courseService.salvar(course);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> atualizar(@PathVariable UUID id, @RequestBody Course course) {
        Course existente = courseService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        course.setCourseId(id);
        Course atualizado = courseService.salvar(course);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        courseService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}