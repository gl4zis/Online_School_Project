package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.RegistrationService;
import ru.spring.school.online.service.UserService;

@Controller
@RequestMapping("/register")
@SessionAttributes({"studentForm", "isSecondStage"})
public class RegisterController {

    private final RegistrationService regService;
    private final UserService userService;

    public RegisterController(RegistrationService regService, UserService userSErvice) {
        this.regService = regService;
        this.userService = userSErvice;
    }

    @ModelAttribute(name = "userForm")
    public User form() {
        return new User();
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("isSecondStage", false);
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("userForm") @Valid User user,
                                      Errors errors,
                                      Model model
    ) {
        if (!userService.setUsernameUnique(user, errors, model) |
                !userService.setEmailUnique(user, errors, model))
            return "register";
        user.setRole(User.Role.STUDENT);
        model.addAttribute("studentForm", user.toStudent());
        model.addAttribute("isSecondStage", true);
        return "register";
    }

    @PutMapping
    public String processRegistration2(@ModelAttribute("studentForm") @Valid Student student,
                                       Errors errors,
                                       HttpServletRequest request,
                                       SessionStatus sessionStatus
    ) {
        if (errors.hasErrors())
            return "register";
        regService.regNewUser(student);
        regService.loginUser(request, sessionStatus, student);
        return "redirect:/profile";
    }
}