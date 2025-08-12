package com.ProjectHub.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    public EnvConfig(){
        //esse metodo carrega o .env
        Dotenv dotenv = Dotenv.configure()
                .directory("src/main/resources") //diretorio que esta o .env
                .ignoreIfMissing() //evita erro se faltar o arquivo
                .load();
        //cada variavel é carregada e é colocada a variavel e o valor nas propriedades do sistema
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        //Agora o Spring pode acessar ${VARIAVEL} como variavel no application.properties
        System.out.println("Variáveis do .env carregadas no Spring Environment");

    }
}