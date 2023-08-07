package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.RegistrationService;
import ru.spring.school.online.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RegistrationService regService;

    public AdminController(UserService userService, RegistrationService regService) {
        this.userService = userService;
        this.regService = regService;
    }

    @ModelAttribute("user")
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin_users";
    }

    @GetMapping("/users/new")
    public String getNewUser(Model model) {
        model.addAttribute("userForm", new User());
        return "adm_teach_register";
    }

    @PostMapping("/users")
    public String processRegister(@ModelAttribute("userForm") @Valid User user,
                                  Errors errors,
                                  Model model,
                                  @RequestParam("_role") String roleName,
                                  HttpServletRequest request,
                                  SessionStatus sessionStatus
    ) {
        if (!userService.setUsernameUnique(user, errors, model) |
                !userService.setEmailUnique(user, errors, model))
            return "adm_teach_register";
        switch (roleName) {
            case "ADMIN" -> user.setRole(User.Role.ADMIN);
            case "TEACHER" -> {
                user.setRole(User.Role.UNCONFIRMED_TEACHER);
                user = user.toTeacher();
            }
        }
        regService.regNewUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable("id") String username,
                                 Model model) {
        User user = userService.findUser(username);
        model.addAttribute("userForm", user);
        return "profile";
    }
}
