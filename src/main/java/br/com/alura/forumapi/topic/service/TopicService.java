package br.com.alura.forumapi.topic.service;

import br.com.alura.forumapi.topic.entity.Topic;
import br.com.alura.forumapi.topic.repository.TopicRepository;
import br.com.alura.forumapi.user.entity.User;
import br.com.alura.forumapi.user.service.UserService;
import br.com.alura.forumapi.course.entity.Course;
import br.com.alura.forumapi.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    /**
     * Lista todos os tópicos ordenados por data de criação (mais recentes primeiro)
     * @return List<Topic>
     */
    public List<Topic> findAll() {
        return topicRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Busca um tópico por ID
     * @param id ID do tópico
     * @return Optional<Topic>
     */
    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    /**
     * Busca tópicos por nome do curso
     * @param courseName nome do curso
     * @return List<Topic>
     */
    public List<Topic> findByCourse(String courseName) {
        return topicRepository.findByCourseNameOrderByCreatedAtDesc(courseName);
    }

    /**
     * Busca tópicos por autor
     * @param authorId ID do autor
     * @return List<Topic>
     */
    public List<Topic> findByAuthor(Long authorId) {
        Optional<User> author = userService.findById(authorId);
        if (author.isPresent()) {
            return topicRepository.findByAuthorOrderByCreatedAtDesc(author.get());
        }
        return List.of(); // Lista vazia se autor não existir
    }

    /**
     * Cria um novo tópico
     * REGRA DE NEGÓCIO: Apenas usuários cadastrados podem criar tópicos
     * @param title título do tópico
     * @param message mensagem do tópico
     * @param courseName nome do curso
     * @param authorUsername username do autor
     * @return Topic criado
     * @throws IllegalArgumentException se dados forem inválidos
     */
    public Topic createTopic(String title, String message, String courseName, String authorUsername) {
        // Validações básicas
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Título não pode estar vazio");
        }

        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem não pode estar vazia");
        }

        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do curso não pode estar vazio");
        }

        // Busca o autor
        Optional<User> author = userService.findByUsername(authorUsername);
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado: " + authorUsername);
        }

        // Busca ou cria o curso
        Course course = courseService.createOrGetCourse(courseName, "Curso de " + courseName);

        // Cria o tópico
        Topic topic = new Topic(title.trim(), message.trim(), author.get(), course);

        return topicRepository.save(topic);
    }

    /**
     * Atualiza um tópico existente
     * REGRA DE NEGÓCIO: Apenas o autor pode atualizar seu próprio tópico
     * @param topicId ID do tópico
     * @param title novo título
     * @param message nova mensagem
     * @param courseName novo nome do curso
     * @param authorUsername username do autor (para validação)
     * @return Topic atualizado
     * @throws IllegalArgumentException se dados forem inválidos
     * @throws SecurityException se usuário não tem permissão
     */
    public Topic updateTopic(Long topicId, String title, String message, String courseName, String authorUsername) {
        // Busca o tópico
        Optional<Topic> existingTopic = topicRepository.findById(topicId);
        if (existingTopic.isEmpty()) {
            throw new IllegalArgumentException("Tópico não encontrado com ID: " + topicId);
        }

        Topic topic = existingTopic.get();

        // VALIDAÇÃO DE SEGURANÇA: Apenas o autor pode atualizar
        if (!topic.getAuthor().getUsername().equals(authorUsername)) {
            throw new SecurityException("Usuário não tem permissão para atualizar este tópico");
        }

        // Validações dos dados
        if (title != null && !title.trim().isEmpty()) {
            topic.setTitle(title.trim());
        }

        if (message != null && !message.trim().isEmpty()) {
            topic.setMessage(message.trim());
        }

        // Atualiza o curso se especificado
        if (courseName != null && !courseName.trim().isEmpty()) {
            Course newCourse = courseService.createOrGetCourse(courseName.trim(), "Curso de " + courseName.trim());
            topic.setCourse(newCourse);
        }

        return topicRepository.save(topic);
    }

    /**
     * Deleta um tópico
     * REGRA DE NEGÓCIO: Apenas o autor pode deletar seu próprio tópico
     * @param topicId ID do tópico
     * @param authorUsername username do autor (para validação)
     * @throws IllegalArgumentException se tópico não for encontrado
     * @throws SecurityException se usuário não tem permissão
     */
    public void deleteTopic(Long topicId, String authorUsername) {
        // Busca o tópico
        Optional<Topic> existingTopic = topicRepository.findById(topicId);
        if (existingTopic.isEmpty()) {
            throw new IllegalArgumentException("Tópico não encontrado com ID: " + topicId);
        }

        Topic topic = existingTopic.get();

        // VALIDAÇÃO DE SEGURANÇA: Apenas o autor pode deletar
        if (!topic.getAuthor().getUsername().equals(authorUsername)) {
            throw new SecurityException("Usuário não tem permissão para deletar este tópico");
        }

        topicRepository.delete(topic);
    }

    /**
     * Verifica se um tópico pertence a um usuário específico
     * @param topicId ID do tópico
     * @param authorUsername username do autor
     * @return true se o tópico pertence ao usuário, false caso contrário
     */
    public boolean isTopicOwner(Long topicId, String authorUsername) {
        Optional<User> author = userService.findByUsername(authorUsername);
        if (author.isEmpty()) {
            return false;
        }

        Optional<Topic> topic = topicRepository.findByIdAndAuthorId(topicId, author.get().getId());
        return topic.isPresent();
    }

    /**
     * Conta o total de tópicos
     * @return número total de tópicos
     */
    public long countTopics() {
        return topicRepository.count();
    }

    /**
     * Conta tópicos por curso
     * @param courseName nome do curso
     * @return número de tópicos do curso
     */
    public long countTopicsByCourse(String courseName) {
        return topicRepository.findByCourseNameOrderByCreatedAtDesc(courseName).size();
    }
}
