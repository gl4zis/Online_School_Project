package ru.spring.school.online.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.spring.school.online.model.security.Student;
import ru.spring.school.online.model.security.Subject;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.RegistrationService;
import ru.spring.school.online.service.SubjectService;
import ru.spring.school.online.service.UserService;

@Controller
@RequestMapping("/profile")
@SessionAttributes("oldUsername")
public class ProfileController {

    private final UserService userService;
    private final SubjectService subjectService;

    private final RegistrationService regService;

    public ProfileController(UserService userService, SubjectService subjectService, RegistrationService regService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.regService = regService;
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
    @PreAuthorize("!hasRole('UNCONFIRMED_TEACHER')")
    public String getProfileSettingsLogin(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("oldUsername", user.getUsername());
        return "profile_settings_login";
    }

    @PatchMapping("/edit/login")
    public String processProfileSettingsLogin(@ModelAttribute("userForm") @Valid User user,
                                              Errors errors,
                                              @ModelAttribute("oldUsername") String oldUsername,
                                              Model model,
                                              SessionStatus session) {
        if (regService.checkRegErrors(user, errors, model)) {
            return "profile_settings_login";
        }
        userService.updateUser(user);
        userService.deleteUser(oldUsername);
        session.isComplete();
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