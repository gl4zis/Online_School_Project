package ru.spring.school.online.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.UserService;

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
    public String register(Model model) {
        model.addAttribute("isSecondStage", false);
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("userForm") @Valid User user,
                                      Errors errors,
                                      Model model,
                                      HttpServletRequest request,
                                      SessionStatus sessionStatus
    ) throws ServletException {
        if (user.getFirstname() == null) {
            boolean isSecondStage = !errors.hasErrors();
            if (!userService.isUsernameUnique(user)) {
                model.addAttribute("usernameUnique", "This username is taken");
                isSecondStage = false;
            }
            model.addAttribute("isSecondStage", isSecondStage);
            return "register";
        } else {
            if (errors.hasErrors()) {
                model.addAttribute("isSecondStage", true);
                return "register";
            }
            userService.saveUser(user, true);
            request.login(user.getUsername(), user.getPassword());
            sessionStatus.isComplete();
            return "redirect:/profile";
        }
    }
}