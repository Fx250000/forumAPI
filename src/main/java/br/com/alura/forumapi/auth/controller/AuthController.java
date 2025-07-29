package br.com.alura.forumapi.auth.controller;

import br.com.alura.forumapi.auth.dto.AuthResponse;
import br.com.alura.forumapi.auth.dto.LoginRequest;
import br.com.alura.forumapi.auth.dto.RegisterRequest;
import br.com.alura.forumapi.auth.service.AuthService;
import br.com.alura.forumapi.config.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        ApiResponse<AuthResponse> response = ApiResponse.success("Login realizado com sucesso", authResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);
        ApiResponse<AuthResponse> response = ApiResponse.success("Usuário registrado com sucesso", authResponse);
        return ResponseEntity.ok(response);
    }
}
