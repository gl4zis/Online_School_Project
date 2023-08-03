package ru.spring.school.online.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.View;
import ru.spring.school.online.config.UserService;
import ru.spring.school.online.model.security.User;

import java.util.Enumeration;

@Controller
@RequestMapping("/register")
@SessionAttributes("userForm")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "userForm")
    public User form() {
        return new User();
    }

    @GetMapping("/admin")
    public String adminRegisterForm() {
        return "admin_register";
    }

    @GetMapping
    public String register() {
        return "register";
    }

    @GetMapping("/continue")
    public String registerContinue(){
        return "register_continue";
    }

    @PostMapping("/continue")
    public String registerContinue(@ModelAttribute("userForm") User user,
                                   HttpServletRequest request, SessionStatus sessionStatus) throws ServletException {
        userService.saveUser(user);
        request.login(user.getUsername(), user.getPasswordConfirm());
        sessionStatus.isComplete();
        return "redirect:/profile";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("userForm") @Valid User user,
                                      Errors errors,
                                      Model model, HttpServletRequest request) {
        if (errors.hasErrors())
            return "register";
        if (!userService.isUsernameUnique(user)){
            model.addAttribute("usernameUnique", "This username is taken");
            return "register";
        }
        model.addAttribute(request);
        return "redirect:/register/continue";
    }
}