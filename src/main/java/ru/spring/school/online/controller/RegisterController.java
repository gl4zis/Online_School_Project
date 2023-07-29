package ru.spring.school.online.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.model.security.RawUserForm;
import ru.spring.school.online.repository.UserRepository;

@Controller
@RequestMapping("/register")
@PermitAll
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin")
    public String adminRegisterForm(@ModelAttribute("userForm") RawUserForm userForm) {
        return "admin_register";
    }

    @PostMapping
    public String registration(@ModelAttribute("userForm") RawUserForm userForm,
                               @RequestParam("role") String role) {
        userForm.setRole(role);
        userRepository.save(userForm.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
