package com.ProjectHub.infrastructure.repository;

import com.ProjectHub.domain.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    Roles findByRole(String role);

}
