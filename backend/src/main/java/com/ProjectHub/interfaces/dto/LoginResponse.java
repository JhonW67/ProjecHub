package com.ProjectHub.interfaces.dto;

public record LoginResponse(String acesssToken, Long expiresIn) {
    // O record LoginResponse é usado para encapsular a resposta de login, contendo o token de acesso e o tempo de expiração.
    // Acessar os valores pode ser feito através dos métodos acessores gerados automaticamente, como acesssToken() e expiresIn().
    // O record é uma forma concisa de criar classes imutáveis em Java, onde
    // os campos são finais e não podem ser modificados após a criação do objeto.
}
