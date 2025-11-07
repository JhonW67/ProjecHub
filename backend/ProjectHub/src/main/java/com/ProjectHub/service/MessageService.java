package com.ProjectHub.service;

import com.ProjectHub.model.Message;
import com.ProjectHub.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public List<Message> listarTodos() {
        return repository.findAll();
    }

    public Message buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Message salvar(Message msg) {
        if (msg.getCreatedAt() == null) {
            msg.setCreatedAt(Timestamp.from(Instant.now()));
        }
        return repository.save(msg);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}

