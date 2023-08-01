package ru.spring.school.online.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import ru.spring.school.online.model.security.RawUserForm;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;

@Controller
@RequestMapping("/register")
@PermitAll
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute(name = "userForm")
    public RawUserForm form() {
        return new RawUserForm();
    }

    @GetMapping("/admin")
    public String adminRegisterForm() {
        return "admin_register";
    }

    @GetMapping
    public String register() {
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("userForm") @Valid RawUserForm userForm,
                                      Errors errors,
                                      HttpServletRequest request
    ) {
        if (errors.hasErrors() || userRepository.existsById(userForm.getUsername()))  //Idk how to show message about this
            return "register";
        User user = userForm.toUser(passwordEncoder);
        userRepository.save(user);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return "redirect:/login";
    }
}
