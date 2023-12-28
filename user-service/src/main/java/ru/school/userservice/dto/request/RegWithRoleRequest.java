package ru.school.userservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.school.userservice.model.User;

@NoArgsConstructor
@Getter
@Setter
public class RegWithRoleRequest extends RegRequest {
    @NotNull(message = "{user.role.null}")
    private User.Role role;
}

