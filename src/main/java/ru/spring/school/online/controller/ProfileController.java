package ru.spring.school.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserRepository userRepo;

    @ModelAttribute("user")
    public User user(@AuthenticationPrincipal User user){
        return user;
    }

    @GetMapping
    public String getProfile(){
        return "profile";
    }

    @GetMapping("/edit")
    public String getProfileSettings(){
        return "profile_settings";
    }

    @PostMapping()
    public String processProfileSettings(@ModelAttribute User user){
        userRepo.save(user);
        return "profile";
    }
}