package ru.spring.school.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @PostMapping
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello From Spring App!");
    }
}
