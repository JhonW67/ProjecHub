package com.ProjectHub.repository;

import com.ProjectHub.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SemesterRepository extends JpaRepository<Semester, UUID> { }