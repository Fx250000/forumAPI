package br.com.alura.forumapi.topic.dto;

import br.com.alura.forumapi.topic.entity.Topic;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class TopicResponse {

    private Long id;
    private String title;
    private String message;
    private String authorUsername;
    private String courseName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Constructors
    public TopicResponse() {}

    public TopicResponse(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.message = topic.getMessage();
        this.authorUsername = topic.getAuthor().getUsername();
        this.courseName = topic.getCourse().getName();
        this.createdAt = topic.getCreatedAt();
        this.updatedAt = topic.getUpdatedAt();
    }

    public TopicResponse(Long id, String title, String message, String authorUsername,
                         String courseName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.authorUsername = authorUsername;
        this.courseName = courseName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
