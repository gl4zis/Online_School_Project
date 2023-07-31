package ru.spring.school.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.school.online.model.security.RawUserForm;

@Controller
@RequestMapping("/login")
public class LoginController {
    @ModelAttribute(name = "userForm")
    public RawUserForm form() {
        return new RawUserForm();
    }

    @GetMapping
    public String login() {
        return "login";
    }
}
