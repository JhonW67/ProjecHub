package com.ProjectHub.interfaces.dto;

public record LoginRequest(String email,String password) {
    public String getEmail() {
    }

    public CharSequence getPassword() {
        return null;
    }
}
