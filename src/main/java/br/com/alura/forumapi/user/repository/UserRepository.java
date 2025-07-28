package br.com.alura.forumapi.user.repository;

import br.com.alura.forumapi.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo username
     * @param username nome de usuário
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca um usuário pelo email
     * @param email email do usuário
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se existe um usuário com o username especificado
     * @param username nome de usuário
     * @return true se existir, false caso contrário
     */
    boolean existsByUsername(String username);

    /**
     * Verifica se existe um usuário com o email especificado
     * @param email email do usuário
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);
}
