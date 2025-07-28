package br.com.alura.forumapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Forum API está funcionando! 🚀";
    }

    @GetMapping("/test")
    public String test() {
        return "Endpoint de teste funcionando! ✅";
    }
}
