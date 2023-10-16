package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotEmpty(message = "{user.username.empty}")
    @Size(message = "{user.username.wrongSize}", max = 20, min = 3)
    @Pattern(message = "{user.username.wrongPattern}", regexp = "^[\\w\\d]+$")
    private final String username;

    @NotEmpty(message = "{user.password.empty}")
    @Size(message = "{user.password.wrongSize}", max = 50, min = 6)
    @Pattern(message = "{user.password.wrongPattern}", regexp = "^\\S+$")
    private final String password;
}
