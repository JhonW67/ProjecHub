package com.ProjectHub.infrastructure.repository;

import com.ProjectHub.domain.IdModel;
import com.ProjectHub.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, IdModel> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);

    boolean existsById(UUID id);

    boolean existsByEmail(@NotBlank @Email @Size(max = 160) String email);

}
