package ru.spring.school.online.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
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
}
