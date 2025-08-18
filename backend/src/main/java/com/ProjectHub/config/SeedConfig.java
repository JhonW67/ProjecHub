package com.ProjectHub.config;

import com.ProjectHub.domain.entity.Project;
import com.ProjectHub.infrastructure.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedConfig {

    @Bean
    CommandLineRunner seedProjects(ProjectRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(Project.builder().name("ProjectHub Core").description("Base do sistema").build());
                repo.save(Project.builder().name("PWA Client").description("Cliente offline-first").build());
            }
        };
    }
}
