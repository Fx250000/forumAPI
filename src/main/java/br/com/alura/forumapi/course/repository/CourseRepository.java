package br.com.alura.forumapi.course.repository;

import br.com.alura.forumapi.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Busca um curso pelo nome (case-insensitive)
     * @param name nome do curso
     * @return Optional<Course>
     */
    Optional<Course> findByNameIgnoreCase(String name);

    /**
     * Verifica se existe
     * @param name nome do curso
     * @return true se existir, false caso contrário
     */
    boolean existsByNameIgnoreCase(String name);
}
