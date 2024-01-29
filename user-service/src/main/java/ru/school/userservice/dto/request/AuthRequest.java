package ru.school.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.school.userservice.validation.email.ValidEmail;
import ru.school.userservice.validation.password.ValidPassword;

@Data
public class AuthRequest {
    @NotNull(message = "{user.email.null}")
    @ValidEmail
    private String email;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;
}
