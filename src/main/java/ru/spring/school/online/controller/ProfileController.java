package ru.spring.school.online.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.model.course.Subject;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.service.StorageService;
import ru.spring.school.online.service.UserService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
   // private final SubjectService subjectService;
    private final StorageService storageService;

/*    @ModelAttribute("userForm")
    public User user(@AuthenticationPrincipal User user) {
        user.setOldUsername(user.getUsername());
        user.setOldEmail(user.getEmail());
        return switch (user.getRole()) {
            case STUDENT -> (Student) user;
            case TEACHER -> (Teacher) user;
            default -> user;
        };
    }*/

/*    @ModelAttribute("subjects")
    public Iterable<Subject> subjects() {
        return subjectService.allSubjects();
    }*/

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

/*    @PatchMapping("/edit/password")
    public String processProfileSettingsPassword(@RequestParam String oldPassword,
                                                 @RequestParam String newPassword,
                                                 @RequestParam String passwordConfirm,
                                                 @AuthenticationPrincipal User user,
                                                 Model model) {
        if (!userService.setNewPassword(oldPassword, newPassword, passwordConfirm,
                user, model))
            return "profile_settings_password";
        user.setPassword(newPassword);
        userService.saveUser(user);
        return "redirect:/logout";
    }*/

/*    @PatchMapping("/edit/login")
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
                                         Model model,
                                         @RequestParam("profilePic") MultipartFile multipartFile) throws IOException {
        if (!userService.setEmailUnique(user, errors, model))
            return "profile_settings";
        if (user.getRole() == User.Role.UNCONFIRMED_TEACHER) {
            user.setRole(User.Role.TEACHER);
        }
        if (storageService.isFileValid(multipartFile)){
            String fileName = storageService.getNormalizedFileName(multipartFile);
            user.setPhotoURL(fileName);
            String uploadDir = storageService.getImgStorageDir() + user.getUsername();
            storageService.saveFile(multipartFile, uploadDir, fileName);
        }
        userService.updateUser(user);
        return "redirect:/profile";
    }*/
}