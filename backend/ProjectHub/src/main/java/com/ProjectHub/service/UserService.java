package com.ProjectHub.service;

import com.ProjectHub.dto.RegisterRequest;
import com.ProjectHub.model.Course;
import com.ProjectHub.model.Semester;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.CourseRepository;
import com.ProjectHub.repository.SemesterRepository;
import com.ProjectHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    public User buscarPorId(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User salvar(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return userRepository.save(user);
    }

    public void deletar(UUID id) {
        userRepository.deleteById(id);
    }

    public User salvarFromRegister(RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setRegistration(req.getRegistration());
        user.setRole(req.getRole());
        user.setActive(true);
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));

        // Para aluno: course e semester
        if ("student".equalsIgnoreCase(req.getRole())) {
            if (req.getCourseId() != null) {
                Course course = courseRepository.findById(req.getCourseId())
                        .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
                user.setCourse(course);
            }
            if (req.getSemesterId() != null) {
                Semester semester = semesterRepository.findById(req.getSemesterId())
                        .orElseThrow(() -> new IllegalArgumentException("Semestre não encontrado"));
                user.setSemester(semester);
            }
        }

        // Para professor: teachingCourses e subjects
        if ("teacher".equalsIgnoreCase(req.getRole())) {
            if (req.getCourseIds() != null && !req.getCourseIds().isEmpty()) {
                List<Course> teachingCourses = courseRepository.findAllById(req.getCourseIds());
                user.setTeachingCourses(teachingCourses);
            }
            if (req.getSubjects() != null) {
                user.setSubjects(req.getSubjects());
            }
        }

        // Se desejar validar ou atribuir algo a mais para "admin", trate aqui...

        return userRepository.save(user);
    }
}
