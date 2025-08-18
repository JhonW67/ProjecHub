package com.ProjectHub.config;


import com.ProjectHub.domain.entity.Roles;
import com.ProjectHub.domain.entity.User;
import com.ProjectHub.infrastructure.repository.RoleRepository;
import com.ProjectHub.infrastructure.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminUserConfig  implements CommandLineRunner {

    private final RoleRepository roleRepository;
    
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByRole(Roles.Values.ADMIN.name());

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> {
                    System.out.printf("admin já existe!");
                },
                () -> {
                    var user = new User();
                    user.setName("admin");
                    user.setPassword_hash(passwordEncoder.encode("admin123"));
                    user.setEmail("admin@admin.com");
                    user.setRoles((Roles) Set.of(roleAdmin));
                    userRepository.save(user);
                    System.out.print("admin criado com sucesso!");
                }
        );

    }


}
