package ru.spring.school.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class RootController {

    @GetMapping
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello From Spring App!");
    }

    @GetMapping("/info")
    public ResponseEntity<String> info(Principal principal) {
        return ResponseEntity.ok(principal.toString());
    }
}
