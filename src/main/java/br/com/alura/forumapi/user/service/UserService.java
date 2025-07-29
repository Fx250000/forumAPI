package br.com.alura.forumapi.user.service;

import br.com.alura.forumapi.user.entity.User;
import br.com.alura.forumapi.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // IMPORTANTE: Use injeção por construtor ao invés de @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Lista todos os usuários
     * @return List<User>
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Busca um usuário por ID
     * @param id ID do usuário
     * @return Optional<User>
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Busca um usuário por username
     * @param username nome de usuário
     * @return Optional<User>
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Busca um usuário por email
     * @param email email do usuário
     * @return Optional<User>
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Cria um novo usuário
     * @param username nome de usuário
     * @param email email do usuário
     * @param password senha do usuário
     * @return User criado
     * @throws IllegalArgumentException se username ou email já existirem
     */
    public User createUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username já existe: " + username);
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já existe: " + email);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    /**
     * Implementação do UserDetailsService para Spring Security
     * @param username nome de usuário
     * @return UserDetails
     * @throws UsernameNotFoundException se usuário não for encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    /**
     * Verifica se um usuário existe pelo username
     * @param username nome de usuário
     * @return true se existir, false caso contrário
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Verifica se um usuário existe pelo email
     * @param email email do usuário
     * @return true se existir, false caso contrário
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
