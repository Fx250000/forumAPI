package br.com.alura.forumapi.topic.controller;


import br.com.alura.forumapi.config.ApiResponse;
import br.com.alura.forumapi.topic.dto.TopicRequest;
import br.com.alura.forumapi.topic.dto.TopicResponse;
import br.com.alura.forumapi.topic.dto.TopicUpdateRequest;
import br.com.alura.forumapi.topic.entity.Topic;
import br.com.alura.forumapi.topic.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> listTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String search) {

        // Criar objeto de ordenação
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Buscar tópicos com filtros
        Page<Topic> topicsPage = topicService.findAllWithFilters(pageable, courseName, search);

        // Converter para Response DTOs
        List<TopicResponse> topicResponses = topicsPage.getContent()
                .stream()
                .map(TopicResponse::new)
                .collect(Collectors.toList());

        // Criar resposta com metadados de paginação
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("topics", topicResponses);
        responseData.put("currentPage", topicsPage.getNumber());
        responseData.put("totalItems", topicsPage.getTotalElements());
        responseData.put("totalPages", topicsPage.getTotalPages());
        responseData.put("pageSize", topicsPage.getSize());
        responseData.put("isFirst", topicsPage.isFirst());
        responseData.put("isLast", topicsPage.isLast());

        ApiResponse<Map<String, Object>> response = ApiResponse.success(
                "Tópicos listados com sucesso",
                responseData
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-topics")
    public ResponseEntity<ApiResponse<List<TopicResponse>>> getMyTopics(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<Topic> myTopics = topicService.findByAuthorUsername(userDetails.getUsername());
        List<TopicResponse> response = myTopics.stream()
                .map(TopicResponse::new)
                .collect(Collectors.toList());

        ApiResponse<List<TopicResponse>> apiResponse = ApiResponse.success(
                "Seus tópicos listados com sucesso",
                response
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/course/{courseName}")
    public ResponseEntity<ApiResponse<List<TopicResponse>>> getTopicsByCourse(
            @PathVariable String courseName) {

        List<Topic> topics = topicService.findByCourse(courseName);
        List<TopicResponse> response = topics.stream()
                .map(TopicResponse::new)
                .collect(Collectors.toList());

        ApiResponse<List<TopicResponse>> apiResponse = ApiResponse.success(
                "Tópicos do curso " + courseName + " listados com sucesso",
                response
        );
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicResponse>> getTopicById(@PathVariable Long id) {
        return topicService.findById(id)
                .map(topic -> {
                    ApiResponse<TopicResponse> response = ApiResponse.success(
                            "Tópico encontrado",
                            new TopicResponse(topic)
                    );
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TopicResponse>> createTopic(
            @RequestBody @Valid TopicRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Topic topic = topicService.createTopic(
                request.getTitle(),
                request.getMessage(),
                request.getCourseName(),
                userDetails.getUsername()
        );

        ApiResponse<TopicResponse> response = ApiResponse.success(
                "Tópico criado com sucesso",
                new TopicResponse(topic)
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TopicResponse>> updateTopic(
            @PathVariable Long id,
            @RequestBody @Valid TopicUpdateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Topic topic = topicService.updateTopic(
                id,
                request.getTitle(),
                request.getMessage(),
                request.getCourseName(),
                userDetails.getUsername()
        );

        ApiResponse<TopicResponse> response = ApiResponse.success(
                "Tópico atualizado com sucesso",
                new TopicResponse(topic)
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTopic(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        topicService.deleteTopic(id, userDetails.getUsername());

        ApiResponse<String> response = ApiResponse.success(
                "Tópico deletado com sucesso",
                null
        );
        return ResponseEntity.ok(response);
    }
}
