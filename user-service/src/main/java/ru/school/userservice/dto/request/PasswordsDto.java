package ru.school.userservice.dto.request;

import lombok.Data;
import ru.school.userservice.validation.password.ValidPassword;

@Data
public class PasswordsDto {
    @ValidPassword
    private String oldPassword;
    @ValidPassword
    private String newPassword;
}
