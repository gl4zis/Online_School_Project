package ru.spring.school.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello From Spring App!");
    }

    @GetMapping("/secured")
    public ResponseEntity<String> securedData() {
        return ResponseEntity.ok("Secured data");
    }
}
