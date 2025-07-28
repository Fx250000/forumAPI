package br.com.alura.forumapi.auth.service;

import br.com.alura.forumapi.auth.dto.AuthResponse;
import br.com.alura.forumapi.auth.dto.LoginRequest;
import br.com.alura.forumapi.auth.dto.RegisterRequest;
import br.com.alura.forumapi.user.entity.User;
import br.com.alura.forumapi.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            User user = (User) userService.loadUserByUsername(loginRequest.getUsername());
            String token = jwtService.generateToken(user.getUsername());

            return new AuthResponse(token, user.getUsername(), user.getEmail());
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        User newUser = userService.createUser(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );

        String token = jwtService.generateToken(newUser.getUsername());

        return new AuthResponse(token, newUser.getUsername(), newUser.getEmail());
    }
}
