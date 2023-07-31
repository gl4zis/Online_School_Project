package ru.spring.school.online.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.spring.school.online.model.security.User;

@Controller
public class RootController {
    @GetMapping
    public String hello(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "hello";
    }
}
