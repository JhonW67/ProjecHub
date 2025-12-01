package com.ProjectHub.service;

import com.ProjectHub.model.Event;
import com.ProjectHub.repository.EventRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @PostConstruct
    public void seedPeiEvents() {
        if (repository.count() > 0) return; // evita duplicar

        repository.save(new Event(
                null,
                "PEI I - Projeto Extensionista Integrador I",
                "Primeira etapa do Projeto Extensionista Integrador, focada em diagnóstico e definição de escopo.",
                "I",
                true
        ));

        repository.save(new Event(
                null,
                "PEI II - Projeto Extensionista Integrador II",
                "Continuidade do PEI com aprofundamento do problema e desenho da solução.",
                "II",
                true
        ));

        repository.save(new Event(
                null,
                "PEI III - Projeto Extensionista Integrador III",
                "Fase de prototipação e validação inicial com a comunidade/parceiros.",
                "III",
                true
        ));

        repository.save(new Event(
                null,
                "PEI IV - Projeto Extensionista Integrador IV",
                "Aprimoramento da solução, consolidação técnica e documentação intermediária.",
                "IV",
                true
        ));

        repository.save(new Event(
                null,
                "PEI V - Projeto Extensionista Integrador V",
                "Implementação ampliada, métricas de impacto e ajustes finais.",
                "V",
                true
        ));

        repository.save(new Event(
                null,
                "PEI VI - Projeto Extensionista Integrador VI",
                "Encerramento do ciclo PEI com apresentação, avaliação final e registro dos aprendizados.",
                "VI",
                true
        ));
    }


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
