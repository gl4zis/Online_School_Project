package ru.school.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.school.userservice.model.User;

@NoArgsConstructor
@Getter
@Setter
public class AuthWithRoleRequest extends AuthRequest {
    @NotNull(message = "{user.role.null}")
    @Size(message = "{user.role.null}", min = 1)
    private User.Role role;
}

