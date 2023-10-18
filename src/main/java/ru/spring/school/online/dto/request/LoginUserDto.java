package ru.spring.school.online.dto.request;

import lombok.Data;
import ru.spring.school.online.validation.password.ValidPassword;
import ru.spring.school.online.validation.username.ValidUsername;

@Data
public class LoginUserDto {
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;
}
