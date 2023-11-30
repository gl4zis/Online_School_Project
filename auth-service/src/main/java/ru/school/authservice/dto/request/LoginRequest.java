package ru.school.authservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.school.authservice.validation.password.ValidPassword;
import ru.school.authservice.validation.username.ValidUsernameOrEmail;

@Data
public class LoginRequest {
    @NotNull(message = "{user.username.null}")
    @ValidUsernameOrEmail
    private String username;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;
}
