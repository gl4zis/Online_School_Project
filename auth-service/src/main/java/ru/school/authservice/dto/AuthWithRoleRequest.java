package ru.school.authservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.school.authservice.model.Account;
import ru.school.authservice.validation.password.ValidPassword;
import ru.school.authservice.validation.username.ValidUsernameOrEmail;

import java.util.Set;

@Data
public class AuthWithRoleRequest {
    @NotNull(message = "{user.username.null}")
    @ValidUsernameOrEmail
    private String username;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;

    @NotNull(message = "{user.role.null}")
    @Size(message = "{user.role.null}", min = 1)
    private Set<Account.Role> roles;
}

