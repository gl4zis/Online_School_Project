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
public class ProfileController {

    private final UserService userService;
    private final SubjectService subjectService;


    public ProfileController(UserService userService, SubjectService subjectService) {
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @ModelAttribute("userForm")
    public User user(@AuthenticationPrincipal User user) {
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
    public String getProfileSettingsLogin(Model model, @AuthenticationPrincipal User user) {
        User copyUser = user.copy();
        model.addAttribute("copyUser", copyUser);
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
        if (!userService.checkOldPassword(oldPassword, user.getPassword())) {
            model.addAttribute("oldPasswordIncorrect", "Wrong password");
            return "profile_settings_password";
        }
        if (!userService.isPasswordValid(newPassword)) {
            model.addAttribute("passwordException", "Password should be longer than 5 characters");
            return "profile_settings_password";
        }
        if (!userService.isPasswordsEquals(newPassword, passwordConfirm)) {
            model.addAttribute("passwordEqualsException", "Passwords should be equals");
            return "profile_settings_password";
        }
        user.setPassword(newPassword);
        userService.saveUser(user, true);
        return "redirect:/logout";
    }

    @PatchMapping("/edit/login")
    public String processProfileSettingsLogin(@ModelAttribute @Valid User copyUser,
                                              Errors errors,
                                              @AuthenticationPrincipal User user,
                                              Model model) {
        model.addAttribute("copyUser", copyUser);
        String oldUsername = user.getUsername();
        String newUsername = copyUser.getUsername();
        String newEmail = copyUser.getEmail();
        if (errors.hasFieldErrors("username") || errors.hasFieldErrors("email"))
            return "profile_settings_login";
        if (!user.getUsername().equals(newUsername)) {
            if (userService.isUsernameUnique(newUsername))
                user.setUsername(newUsername);
            else {
                model.addAttribute("usernameUnique", "Username is taken");
                return "profile_settings_login";
            }
        }
        if (!user.getEmail().equals(newEmail)) {
            if (userService.isEmailUnique(newEmail))
                user.setEmail(newEmail);
            else {
                model.addAttribute("emailUnique", "Email is taken");
                return "profile_settings_login";
            }
        }
        userService.deleteUser(oldUsername);
        userService.updateUser(user);
        return "redirect:/logout";
    }

    @PatchMapping
    public String processProfileSettings(@ModelAttribute("userForm") @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return "profile_settings";
        }
        if (user.getRole() == User.Role.UNCONFIRMED_TEACHER) {
            user.setRole(User.Role.TEACHER);
        }
        userService.updateUser(user);
        return "redirect:/profile";
    }
}