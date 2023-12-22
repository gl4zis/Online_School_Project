package ru.school.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.school.userservice.validation.name.ValidName;
import ru.school.userservice.validation.password.ValidPassword;
import ru.school.userservice.validation.username.ValidUsername;

@Data
public class RegRequest {
    @NotNull(message = "{user.username.null}")
    @ValidUsername
    private String username;
    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;
    @NotNull(message = "{user.name.null}")
    @ValidName
    private String firstname;
    @NotNull(message = "{user.name.null}")
    @ValidName
    private String lastname;
}
