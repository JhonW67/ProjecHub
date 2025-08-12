package com.ProjectHub.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController //indica que essa classe responde requisições REST
public class HealthController {
    @GetMapping("/health") //cria um endpoint HTTP GET na rota /health
    public Map<String, String> health() {
        //O metodo health() retorna um mapa com chave "status" e valor "ok".
        return Map.of("status", "ok");
    }
}