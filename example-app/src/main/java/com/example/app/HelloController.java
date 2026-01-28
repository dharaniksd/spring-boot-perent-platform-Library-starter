package com.example.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello! This application is secured by Security Platform Starter");
        response.put("status", "All vulnerabilities are managed centrally");
        return response;
    }

    @GetMapping("/api/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Example App");
        response.put("security", "Enabled");
        response.put("platform", "Security Platform Starter v1.0.0");
        return response;
    }
}
