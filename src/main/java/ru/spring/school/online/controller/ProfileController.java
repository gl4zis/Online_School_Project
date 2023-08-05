package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class ProfileController {

    private final UserService userService;
    private final SubjectService subjectService;
    private final RegistrationService registrationService;

    public ProfileController(UserService userService, SubjectService subjectService, RegistrationService registrationService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.registrationService = registrationService;
    }

    @ModelAttribute("userForm")
    public User user(@AuthenticationPrincipal User user) {
        System.out.println(user);
        return switch (user.getRole()) {
            case UNCONFIRMED_TEACHER -> user.toTeacher();
            case STUDENT -> (Student) user;
            case TEACHER -> (Teacher) user;
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

    @PatchMapping
    public String processProfileSettings(@ModelAttribute("userForm") @Valid User user, Errors errors, HttpServletRequest request, SessionStatus session) {
        if (errors.hasErrors()) {
            return "profile_settings";
        }
        if (user.getRole() == User.Role.UNCONFIRMED_TEACHER) {
            user.setRole(User.Role.TEACHER);
            userService.deleteUser(user.getUsername());
        }
        userService.updateUser(user);
        return "redirect:/profile";
    }
}