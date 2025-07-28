package br.com.alura.forumapi.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TopicRequest {

    @NotBlank(message = "Título não pode estar vazio")
    @Size(min = 5, max = 200, message = "Título deve ter entre 5 e 200 caracteres")
    private String title;

    @NotBlank(message = "Mensagem não pode estar vazia")
    @Size(min = 10, max = 2000, message = "Mensagem deve ter entre 10 e 2000 caracteres")
    private String message;

    @NotBlank(message = "Nome do curso não pode estar vazio")
    @Size(min = 2, max = 100, message = "Nome do curso deve ter entre 2 e 100 caracteres")
    private String courseName;

    // Constructors
    public TopicRequest() {}

    public TopicRequest(String title, String message, String courseName) {
        this.title = title;
        this.message = message;
        this.courseName = courseName;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
