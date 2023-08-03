package ru.spring.school.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.school.online.model.security.User;

@Controller
@RequestMapping("/login")
public class LoginController {
    @ModelAttribute(name = "userForm")
    public User form() {
        return new User();
    }

    @GetMapping
    public String login() {
        return "login";
    }
}
