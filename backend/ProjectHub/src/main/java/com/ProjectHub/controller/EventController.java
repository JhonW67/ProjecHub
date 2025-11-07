package com.ProjectHub.controller;

import com.ProjectHub.model.Event;
import com.ProjectHub.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService service;

    @GetMapping
    public ResponseEntity<List<Event>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> buscar(@PathVariable UUID id) {
        Event obj = service.buscarPorId(id);
        if (obj == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Event> criar(@RequestBody Event obj) {
        Event salvo = service.salvar(obj);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> atualizar(@PathVariable UUID id, @RequestBody Event obj) {
        Event existente = service.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        obj.setEventId(id);
        Event atualizado = service.salvar(obj);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

