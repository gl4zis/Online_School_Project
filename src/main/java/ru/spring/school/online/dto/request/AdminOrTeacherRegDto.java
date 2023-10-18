package ru.spring.school.online.dto.request;

import lombok.Data;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.role.ValidRole;
import ru.spring.school.online.validation.username.ValidUsername;

@Data
public class AdminOrTeacherRegDto {
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidRole(anyOf = {User.Role.ADMIN, User.Role.UNCONFIRMED_TEACHER})
    private User.Role role;
}
