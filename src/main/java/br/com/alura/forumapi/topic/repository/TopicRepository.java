package br.com.alura.forumapi.topic.repository;

import br.com.alura.forumapi.topic.entity.Topic;
import br.com.alura.forumapi.user.entity.User;
import br.com.alura.forumapi.course.entity.Course;
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
     * Busca tópicos por curso
     * @param course curso
     * @return List<Topic>
     */
    List<Topic> findByCourseOrderByCreatedAtDesc(Course course);

    /**
     * Busca tópicos por autor
     * @param author autor dos tópicos
     * @return List<Topic>
     */
    List<Topic> findByAuthorOrderByCreatedAtDesc(User author);

    /**
     * Busca tópicos por nome do curso (usando join)
     * @param courseName nome do curso
     * @return List<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE t.course.name = :courseName ORDER BY t.createdAt DESC")
    List<Topic> findByCourseNameOrderByCreatedAtDesc(@Param("courseName") String courseName);

    /**
     * Verifica se um tópico pertence a um usuário específico
     * @param topicId ID do tópico
     * @param authorId ID do autor
     * @return Optional<Topic>
     */
    @Query("SELECT t FROM Topic t WHERE t.id = :topicId AND t.author.id = :authorId")
    Optional<Topic> findByIdAndAuthorId(@Param("topicId") Long topicId, @Param("authorId") Long authorId);
}
