package br.com.alura.forumapi.course.service;

import br.com.alura.forumapi.course.entity.Course;
import br.com.alura.forumapi.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Lista todos os cursos
     * @return List<Course>
     */
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    /**
     * Busca um curso por ID
     * @param id ID do curso
     * @return Optional<Course>
     */
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * Busca um curso por nome (case-insensitive)
     * @param name nome do curso
     * @return Optional<Course>
     */
    public Optional<Course> findByName(String name) {
        return courseRepository.findByNameIgnoreCase(name);
    }

    /**
     * Cria um novo curso ou retorna existente
     * @param name nome do curso
     * @param description descrição do curso
     * @return Course
     */
    public Course createOrGetCourse(String name, String description) {
        Optional<Course> existingCourse = courseRepository.findByNameIgnoreCase(name);

        if (existingCourse.isPresent()) {
            return existingCourse.get();
        }

        Course newCourse = new Course(name, description);
        return courseRepository.save(newCourse);
    }

    /**
     * Verifica se um curso existe pelo nome
     * @param name nome do curso
     * @return true se existir, false caso contrário
     */
    public boolean existsByName(String name) {
        return courseRepository.existsByNameIgnoreCase(name);
    }
}
