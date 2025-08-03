package br.com.alura.forumapi.stats.controller;

import br.com.alura.forumapi.config.ApiResponse;
import br.com.alura.forumapi.topic.service.TopicService;
import br.com.alura.forumapi.user.service.UserService;
import br.com.alura.forumapi.course.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final TopicService topicService;
    private final UserService userService;
    private final CourseService courseService;

    public StatsController(TopicService topicService, UserService userService, CourseService courseService) {
        this.topicService = topicService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalTopics", topicService.countTopics());
        stats.put("totalUsers", userService.findAll().size());
        stats.put("totalCourses", courseService.findAll().size());

        ApiResponse<Map<String, Object>> response = ApiResponse.success(
                "Estatísticas obtidas com sucesso",
                stats
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/course/{courseName}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCourseStats(@PathVariable String courseName) {
        Map<String, Object> stats = new HashMap<>();

        stats.put("courseName", courseName);
        stats.put("totalTopics", topicService.countTopicsByCourse(courseName));

        ApiResponse<Map<String, Object>> response = ApiResponse.success(
                "Estatísticas do curso obtidas com sucesso",
                stats
        );

        return ResponseEntity.ok(response);
    }
}
