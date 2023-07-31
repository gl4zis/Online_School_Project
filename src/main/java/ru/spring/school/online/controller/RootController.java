package ru.spring.school.online.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.spring.school.online.model.security.User;

@Controller
public class RootController {
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping
    public String hello(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "hello";
    }

    @GetMapping("/logout")
    public String logout(Authentication auth, HttpServletRequest req, HttpServletResponse resp) {
        logoutHandler.logout(req, resp, auth);
        return "redirect:/login";
    }
}
