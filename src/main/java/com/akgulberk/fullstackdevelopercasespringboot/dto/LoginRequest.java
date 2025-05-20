package com.akgulberk.fullstackdevelopercasespringboot.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    // Getter metodları
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setter metodları
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
} 