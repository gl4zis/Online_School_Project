package ru.spring.school.online.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userForm")
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/edit")
    public String getProfileSettings() {
        return "profile_settings";
    }

    @PatchMapping
    public String processProfileSettings(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/profile";
    }
}