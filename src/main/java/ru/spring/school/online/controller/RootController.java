package ru.spring.school.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    //TODO: Remove on release
    @GetMapping
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello From Spring App!");
    }

    @GetMapping("/info")
    public ResponseEntity<String> info(Authentication auth) {
        return ResponseEntity.ok(auth.toString());
    }
}
