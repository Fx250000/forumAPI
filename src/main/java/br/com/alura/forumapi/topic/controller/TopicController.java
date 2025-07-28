package br.com.alura.forumapi.topic.controller;

import br.com.alura.forumapi.topic.dto.TopicRequest;
import br.com.alura.forumapi.topic.dto.TopicResponse;
import br.com.alura.forumapi.topic.dto.TopicUpdateRequest;
import br.com.alura.forumapi.topic.entity.Topic;
import br.com.alura.forumapi.topic.service.TopicService;
import br.com.alura.forumapi.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final UserService userService;

    public TopicController(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> listAllTopics() {
        List<Topic> topics = topicService.findAll();
        List<TopicResponse> response = topics.stream().map(TopicResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long id) {
        return topicService.findById(id)
                .map(topic -> ResponseEntity.ok(new TopicResponse(topic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@RequestBody @Valid TopicRequest request,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        Topic topic = topicService.createTopic(
                request.getTitle(),
                request.getMessage(),
                request.getCourseName(),
                userDetails.getUsername()
        );
        return ResponseEntity.ok(new TopicResponse(topic));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponse> updateTopic(@PathVariable Long id,
                                                     @RequestBody @Valid TopicUpdateRequest request,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Topic topic = topicService.updateTopic(
                    id,
                    request.getTitle(),
                    request.getMessage(),
                    request.getCourseName(),
                    userDetails.getUsername()
            );
            return ResponseEntity.ok(new TopicResponse(topic));
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            topicService.deleteTopic(id, userDetails.getUsername());
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(403).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
