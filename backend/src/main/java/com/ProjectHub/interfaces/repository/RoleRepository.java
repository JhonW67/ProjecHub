package com.ProjectHub.interfaces.repository;

import com.ProjectHub.domain.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    Roles findByRole(String role);

}
