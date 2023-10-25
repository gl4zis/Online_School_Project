package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.username.ValidUsername;

import java.util.Set;

@Data
public class AdminOrTeacherRegDto {
    @NotNull(message = "{user.username.null}")
    @ValidUsername
    private String username;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;

    @NotNull(message = "{user.role.null}")
    @Size(message = "{user.role.null}", min = 1)
    private Set<User.Role> roles;
}
