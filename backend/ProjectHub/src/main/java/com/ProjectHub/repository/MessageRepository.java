package com.ProjectHub.repository;

import com.ProjectHub.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> { }

