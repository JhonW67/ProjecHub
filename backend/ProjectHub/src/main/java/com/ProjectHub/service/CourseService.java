package com.ProjectHub.service;

import com.ProjectHub.model.Course;
import com.ProjectHub.repository.CourseRepository;
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