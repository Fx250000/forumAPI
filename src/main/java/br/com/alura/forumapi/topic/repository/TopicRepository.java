package br.com.alura.forumapi.topic.repository;

import br.com.alura.forumapi.topic.entity.Topic;
import br.com.alura.forumapi.user.entity.User;
import br.com.alura.forumapi.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Busca todos os tópicos ordenados por data de criação (mais recentes primeiro)
     * @return List<Topic>
     */
    List<Topic> findAllByOrderByCreatedAtDesc();

    /**
     * Busca paginada de todos os tópicos
     * @param pageable configuração de paginação
     * @return Page<Topic>
     */
    Page<Topic> findAll(Pageable pageable);

    /**
     * Busca tópicos por curso com paginação
     * @param course curso
     * @param pageable configuração de paginação
     * @return Page<Topic>
     */
    Page<Topic> findByCourse(Course course, Pageable pageable);

    /**
     * Busca tópicos por autor
     * @param author autor dos tópicos
     * @return List<Topic>
     */
    List<Topic> findByAuthorOrderByCreatedAtDesc(User author);

    /**
     * Busca tópicos por autor username
     * @param username username do autor
     * @return List<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE t.author.username = :username ORDER BY t.createdAt DESC")
    List<Topic> findByAuthorUsernameOrderByCreatedAtDesc(@Param("username") String username);

    /**
     * Busca tópicos por nome do curso (usando join)
     * @param courseName nome do curso
     * @return List<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName ORDER BY t.createdAt DESC")
    List<Topic> findByCourseNameOrderByCreatedAtDesc(@Param("courseName") String courseName);

    /**
     * Busca tópicos por nome do curso com paginação
     * @param courseName nome do curso
     * @param pageable configuração de paginação
     * @return Page<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName")
    Page<Topic> findByCourseNameWithPagination(@Param("courseName") String courseName, Pageable pageable);

    /**
     * Busca tópicos por texto (título ou mensagem) com paginação
     * @param searchText texto a ser buscado
     * @param pageable configuração de paginação
     * @return Page<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.message) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Topic> findByTitleOrMessageContainingIgnoreCase(@Param("searchText") String searchText, Pageable pageable);

    /**
     * Busca avançada com filtros múltiplos
     * @param courseName nome do curso (opcional)
     * @param searchText texto de busca (opcional)
     * @param pageable configuração de paginação
     * @return Page<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE " +
            "(:courseName IS NULL OR t.course.name = :courseName) AND " +
            "(:searchText IS NULL OR " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.message) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<Topic> findWithFilters(@Param("courseName") String courseName,
                                @Param("searchText") String searchText,
                                Pageable pageable);

    /**
     * Verifica se um tópico pertence a um usuário específico
     * @param topicId ID do tópico
     * @param authorId ID do autor
     * @return Optional<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE t.id = :topicId AND t.author.id = :authorId")
    Optional<Topic> findByIdAndAuthorId(@Param("topicId") Long topicId, @Param("authorId") Long authorId);

    /**
     * Conta tópicos por curso
     * @param courseName nome do curso
     * @return número de tópicos
     */
    @Query("SELECT COUNT(t) FROM Topic t WHERE t.course.name = :courseName")
    Long countByCourse(@Param("courseName") String courseName);
}
