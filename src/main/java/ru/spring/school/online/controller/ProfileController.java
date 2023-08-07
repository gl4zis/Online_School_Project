package ru.spring.school.online.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Subject;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.SubjectService;
import ru.spring.school.online.service.UserService;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"copyUser", "userForm"})
public class ProfileController {

    private final UserService userService;
    private final SubjectService subjectService;


    public ProfileController(UserService userService, SubjectService subjectService) {
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @ModelAttribute("userForm")
    public User user(@AuthenticationPrincipal User user) {
        user.setOldUsername(user.getUsername());
        user.setOldEmail(user.getEmail());
        return switch (user.getRole()) {
            case STUDENT -> (Student) user;
            case TEACHER, UNCONFIRMED_TEACHER -> (Teacher) user;
            default -> user;
        };
    }

    @ModelAttribute("subjects")
    public Iterable<Subject> subjects() {
        return subjectService.allSubjects();
    }

    @GetMapping
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/edit")
    public String getProfileSettings() {
        return "profile_settings";
    }

    @GetMapping("/edit/login")
    public String getProfileSettingsLogin() {
        return "profile_settings_login";
    }

    @GetMapping("/edit/password")
    public String getProfileSettingsPassword(Model model) {
        return "profile_settings_password";
    }

    @PatchMapping("/edit/password")
    public String processProfileSettingsPassword(@RequestParam String oldPassword,
                                                 @RequestParam String newPassword,
                                                 @RequestParam String passwordConfirm,
                                                 @AuthenticationPrincipal User user,
                                                 Model model) {
        if (!userService.setNewPassword(oldPassword, newPassword, passwordConfirm,
                user, model))
            return "profile_settings_password";
        user.setPassword(newPassword);
        userService.saveUser(user, true);
        return "redirect:/logout";
    }

    @PatchMapping("/edit/login")
    public String processProfileSettingsLogin(@ModelAttribute("userForm") @Valid User user,
                                              Errors errors,
                                              Model model) {
        if (!userService.setUsernameUnique(user, errors, model))
            return "profile_settings_login";
        userService.deleteUser(user.getOldUsername());
        userService.updateUser(user);
        return "redirect:/logout";
    }

    @PatchMapping("/edit")
    public String processProfileSettings(@ModelAttribute("userForm") @Valid User user,
                                         Errors errors,
                                         Model model) {
        if (!userService.setEmailUnique(user, errors, model))
            return "profile_settings";
        if (user.getRole() == User.Role.UNCONFIRMED_TEACHER) {
            user.setRole(User.Role.TEACHER);
        }
        userService.updateUser(user);
        return "redirect:/profile";
    }
}