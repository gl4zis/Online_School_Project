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
@SessionAttributes({"userForm", "isSecondStage"})
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
                                      @ModelAttribute("isSecondStage") boolean isSecondStage,
                                      Model model,
                                      HttpServletRequest request,
                                      SessionStatus sessionStatus
    ) throws ServletException {
        if (!isSecondStage) {
            return registerFirstStage(user, model, errors);
        } else {
            return registerSecondStage(user, model, errors,
                    request, sessionStatus);
        }
    }

    public boolean checkingRegisterErrors(User user, Model model, Errors errors) {
        if (errors.hasErrors())
            return true;
        if (!userService.isUsernameUnique(user)) {
            model.addAttribute("usernameUnique", "This username is taken");
            return true;
        }
        return false;
    }

    public String registerFirstStage(User user, Model model, Errors errors) {
        if (checkingRegisterErrors(user, model, errors))
            return "register";
        model.addAttribute("isSecondStage", true);
        user.setRole(User.Role.STUDENT);
        user.setGotUsername(true);
        return "register";
    }

    public String registerSecondStage(User user, Model model, Errors errors,
                                      HttpServletRequest request,
                                      SessionStatus sessionStatus) {
        if (checkingRegisterErrors(user, model, errors))
            return "register";
        userService.saveUser(user, true);
        loginUser(request, sessionStatus, user);
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