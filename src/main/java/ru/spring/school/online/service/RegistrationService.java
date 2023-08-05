package ru.spring.school.online.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;
import ru.spring.school.online.model.security.User;

@Service
public class RegistrationService {

    private final UserService userService;

    public RegistrationService(UserService userService) {
        this.userService = userService;
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

    public boolean regNewUser(User user) {
        if (!userService.isUsernameUnique(user))
            return false;
        userService.saveUser(user, true);
        return true;
    }

    /**
     * true if there are errors!!
     */
    public boolean checkRegErrors(User user, Errors errors, Model model) {
        if (errors.hasErrors())
            return true;
        if (!userService.isUsernameUnique(user)) {
            model.addAttribute("usernameUnique", "This username is taken");
            return true;
        }
        if (!userService.isEmailUnique(user)) {
            model.addAttribute("emailUnique", "This email is taken");
            return true;
        }
        return false;
    }
}
