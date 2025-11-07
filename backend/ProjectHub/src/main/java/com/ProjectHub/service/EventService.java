package com.ProjectHub.service;

import com.ProjectHub.model.Event;
import com.ProjectHub.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public List<Event> listarTodos() {
        return repository.findAll();
    }

    public Event buscarPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Event salvar(Event event) {
        return repository.save(event);
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
