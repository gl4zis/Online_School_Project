package ru.spring.school.online.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.role.ValidRole;
import ru.spring.school.online.validation.username.ValidUsername;

@Data
public class AdminOrTeacherRegDto {
    @NotNull(message = "{user.username.null}")
    @ValidUsername
    private String username;

    @NotNull(message = "{user.password.null}")
    @ValidPassword
    private String password;

    @NotNull(message = "{user.role.null}")
    @ValidRole(anyOf = {User.Role.ADMIN, User.Role.UNCONFIRMED_TEACHER})
    private User.Role role;
}
