package br.com.alura.forumapi.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Username não pode estar vazio")
    @Size(min = 3, max = 15, message = "Username deve ter entre 3 e 15 caracteres")
    private String username;

    @NotBlank(message = "Password não pode estar vazio")
    @Size(min = 6, message = "Password deve ter 6 ou mais caracteres")
    private String password;

    // Constructors
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
