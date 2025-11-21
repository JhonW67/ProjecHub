package com.ProjectHub.service;

import com.ProjectHub.model.Course;
import com.ProjectHub.repository.CourseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @PostConstruct
    public void popularCursosDefault() {
        if (courseRepository.count() == 0) {
            courseRepository.save(Course.builder()
                    .name("Engenharia de Software")
                    .description("Curso de Engenharia de Software")
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build());
            courseRepository.save(Course.builder()
                    .name("Ciência da Computação")
                    .description("Curso de Ciência da Computação")
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build());
            courseRepository.save(Course.builder()
                    .name("Sistemas de informação")
                    .description("Curso de Sistemas de Informação")
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build());
            // Adicione mais cursos conforme necessidade
        }
    }

    public List<Course> listarTodos() {
        return courseRepository.findAll();
    }

    public Course buscarPorId(UUID id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course salvar(Course course) {
        if (course.getCreatedAt() == null) {
            course.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return courseRepository.save(course);
    }

    public void deletar(UUID id) {
        courseRepository.deleteById(id);
    }
}