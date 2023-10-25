package ru.spring.school.online.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.username.ValidUsernameOrEmail;

@Data
@Schema(description = "Entity for login")
public class LoginUserDto {
    @Schema(description = "Username|Email")
    @NotNull(message = "{user.username.null}")
    @ValidUsernameOrEmail
    private String username;

    @Schema(description = "Password")
    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;
}
