package ru.spring.school.online.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.spring.school.online.dto.request.AdminOrTeacherRegDto;
import ru.spring.school.online.dto.response.MessageResponse;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.service.AuthService;
import ru.spring.school.online.service.UserService;
import ru.spring.school.online.utils.DtoMappingUtils;

@RestController
@Tag(name = "Controller for admins tools", description = "All admins functionality works through it")
@SecurityRequirement(name = "JWT")
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final DtoMappingUtils dtoMappingUtils;
    private final AuthService authService;
    private final UserService userService;

    @Operation(summary = "Register any user",
            description = "Admin can register new user, set any roles for this account through it")
    @PostMapping("/register")
    @ResponseBody
    public MessageResponse regAdmin(@RequestBody AdminOrTeacherRegDto regDto) {
        authService.registerUtil(dtoMappingUtils.newUser(regDto));
        return new MessageResponse("User was successfully registered");
    }

    @Operation(summary = "Returns all registered accounts")
    @GetMapping("/users")
    @ResponseBody
    public Iterable<ProfileInfo> getAllUsers() {
        return userService.getAll();
    }

    @Operation(summary = "Lock/Unlock user by username")
    @PatchMapping("/users/{username}")
    @ResponseBody
    public MessageResponse lockUnlockUser(@PathVariable("username") String username,
                                          @RequestParam("lock") Boolean lock) {
        userService.changeUserLock(username, lock);
        return new MessageResponse("User account was changed");
    }
}
