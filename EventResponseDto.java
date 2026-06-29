package com.example.lab4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actuator")
public class ActuatorController {
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "lab4-ai-244-tkachenko"));
    }

    @GetMapping("/metrics")
    public ResponseEntity<List<String>> metrics() {
        return ResponseEntity.ok(List.of("http.server.requests", "jvm.memory.used", "process.uptime"));
    }

    @GetMapping("/prometheus")
    public ResponseEntity<String> prometheus() {
        return ResponseEntity.ok("service_status 1\nservice_time \"" + LocalDateTime.now() + "\"");
    }
}
