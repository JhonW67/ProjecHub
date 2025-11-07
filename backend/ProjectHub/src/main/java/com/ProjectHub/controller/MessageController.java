package com.ProjectHub.controller;

import com.ProjectHub.model.Message;
import com.ProjectHub.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping
    public ResponseEntity<List<Message>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> buscar(@PathVariable Long id) {
        Message obj = service.buscarPorId(id);
        if (obj == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Message> criar(@RequestBody Message obj) {
        Message salvo = service.salvar(obj);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> atualizar(@PathVariable Long id, @RequestBody Message obj) {
        Message existente = service.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        obj.setMessageId(id);
        Message atualizado = service.salvar(obj);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

