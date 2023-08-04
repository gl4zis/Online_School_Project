package ru.spring.school.online.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.UserService;

@Controller
@RequestMapping("/register")
@SessionAttributes({"studentForm", "isSecondStage"})
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

    @GetMapping("/continue")
    public String registerContinue(Model model) {
        model.addAttribute("isSecondStage", true);
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("userForm") @Valid User user,
                                      Errors errors,
                                      Model model
    ) throws ServletException {
        if (errors.hasErrors())
            return "register";
        if (!userService.isUsernameUnique(user)) {
            model.addAttribute("usernameUnique", "This username is taken");
            return "register";
        }
        if (!userService.isEmailUnique(user)) {
            model.addAttribute("emailUnique", "This email is taken");
            return "register";
        }
        user.setRole(User.Role.STUDENT);
        model.addAttribute("studentForm", user.toStudent());
        return "redirect:/register/continue";
    }

    @PostMapping("/continue")
    public String processRegistrationContinue(@ModelAttribute("studentForm") @Valid Student student,
                                      Errors errors,
                                      Model model,
                                      HttpServletRequest request,
                                      SessionStatus sessionStatus
    ) throws ServletException {
        if (errors.hasErrors())
            return "register";
        userService.saveUser(student, true);
        loginUser(request, sessionStatus, student);
        return "redirect:/profile";
    }


    public void loginUser(HttpServletRequest request,
                          SessionStatus sessionStatus,
                          User user) {
        try {
            request.login(user.getUsername(), user.getPassword());
            sessionStatus.isComplete();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}