package ru.spring.school.online.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.model.security.RawUserForm;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;

@Controller
@RequestMapping("/register")
@PermitAll
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute(name = "userForm")
    public RawUserForm form(){
        return new RawUserForm();
    }

    @GetMapping("/admin")
    public String adminRegisterForm() {
        return "admin_register";
    }

    @GetMapping
    public String register(){
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("userForm") RawUserForm userForm,
                                      @RequestParam("role") String role, HttpServletRequest request) {
        userForm.setRole(role);
        User user = userForm.toUser(passwordEncoder);
        userRepository.save(user);
        authAfterRegistration(request, userForm.getUsername(), userForm.getPassword());
        return "redirect:/profile";
    }

    public void authAfterRegistration(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);

        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

}
