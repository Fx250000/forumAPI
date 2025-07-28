package br.com.alura.forumapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username não pode estar vazio")
    @Size(min = 3, max = 15, message = "Username deve ter entre 3 e 15 caracteres")
    private String username;

    @NotBlank(message = "Email não pode estar vazio")
    @Email(message = "Email deve ter um formato válido")
    @Size(max = 100, message = "Email não pode ter mais de 100 caracteres")
    private String email;

    @NotBlank(message = "Password não pode estar vazio")
    @Size(min = 6, message = "Password deve ter mais de 6 caracteres")
    private String password;

    // Constructors
    public RegisterRequest() {}

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
