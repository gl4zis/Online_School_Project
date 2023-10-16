package ru.spring.school.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AuthService regService;

    @ModelAttribute("user")
    public User user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin_users";
    }

    @GetMapping("/users/new")
    public String getNewUser(Model model) {
        model.addAttribute("userForm", new User());
        return "adm_teach_register";
    }

/*    @PostMapping("/users")
    public String processRegister(@ModelAttribute("userForm") @Valid User user,
                                  Errors errors,
                                  Model model,
                                  @RequestParam("_role") String roleName,
                                  HttpServletRequest request,
                                  SessionStatus sessionStatus
    ) {
        if (!userService.setUsernameUnique(user, errors, model) |
                !userService.setEmailUnique(user, errors, model))
            return "adm_teach_register";
        switch (roleName) {
            case "ADMIN" -> user.setRole(User.Role.ADMIN);
            case "TEACHER" -> {
                user.setRole(User.Role.UNCONFIRMED_TEACHER);
                user = user.toTeacher();
            }
        }
        regService.regNewUser(user);
        return "redirect:/admin/users";
    }*/

/*    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable("id") String username,
                                 Model model) {
        User user = userService.findUser(username);
        model.addAttribute("userForm", user);
        return "profile";
    }*/
}
