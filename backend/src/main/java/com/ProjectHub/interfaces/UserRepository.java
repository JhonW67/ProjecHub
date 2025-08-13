package com.ProjectHub.interfaces;

import com.ProjectHub.domain.IdModel;
import com.ProjectHub.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, IdModel> {
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    UserModel findByEmail(String email);
    UserModel findByUsername(String username);
}
