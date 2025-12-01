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
        if (repository.count() > 0) return;

        repository.save(Event.builder()
                .title("PEI I - Projeto Extensionista Integrador I")
                .description("Primeira etapa do Projeto Extensionista Integrador, focada em diagnóstico e definição de escopo.")
                // não seta startAt/endAt => vão como null
                .build()
        );

        repository.save(Event.builder()
                .title("PEI II - Projeto Extensionista Integrador II")
                .description("Continuidade do PEI com aprofundamento do problema e desenho da solução.")
                .build()
        );

        repository.save(Event.builder()
                .title("PEI III - Projeto Extensionista Integrador III")
                .description("Fase de prototipação e validação inicial com a comunidade/parceiros.")
                .build()
        );

        repository.save(Event.builder()
                .title("PEI IV - Projeto Extensionista Integrador IV")
                .description("Aprimoramento da solução, consolidação técnica e documentação intermediária.")
                .build()
        );

        repository.save(Event.builder()
                .title("PEI V - Projeto Extensionista Integrador V")
                .description("Implementação ampliada, métricas de impacto e ajustes finais.")
                .build()
        );

        repository.save(Event.builder()
                .title("PEI VI - Projeto Extensionista Integrador VI")
                .description("Encerramento do ciclo PEI com apresentação, avaliação final e registro dos aprendizados.")
                .build()
        );
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

