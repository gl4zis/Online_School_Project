package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.username.ValidUsernameOrEmail;

@Data
public class LoginUserDto {
    @NotNull(message = "{user.username.null}")
    @ValidUsernameOrEmail
    private String username;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;
}
