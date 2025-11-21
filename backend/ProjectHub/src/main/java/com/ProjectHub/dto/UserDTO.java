package com.ProjectHub.dto;

import com.ProjectHub.model.User;
import lombok.Data;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID userId;
    private String name;
    private String email;
    private String role;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public UserDTO(UUID userId, String name, String role) {
    }
}
