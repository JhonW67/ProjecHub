package com.ProjectHub.interfaces.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    @NotBlank @Email @Size(max = 160)
    private String email;
    @NotBlank @Size(min = 6, max = 60)
    private String password;
    @NotBlank @Size(min = 2, max = 120)
    private String name;


}