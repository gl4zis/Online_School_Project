package ru.school.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.school.authservice.validation.password.ValidPassword;
import ru.school.authservice.validation.username.ValidUsernameOrEmail;

@Data
public class AuthRequest {
    @NotNull(message = "{user.username.null}")
    @ValidUsernameOrEmail
    private String username;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;
}
