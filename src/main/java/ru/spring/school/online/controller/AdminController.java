package ru.spring.school.online.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepo;

    public AdminController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @ModelAttribute("user")
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "admin";
    }
}
