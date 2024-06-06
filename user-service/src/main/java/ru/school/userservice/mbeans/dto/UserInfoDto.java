package ru.school.userservice.mbeans.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.school.userservice.model.User;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private User.Role role;
    private long timestamp;

    public UserInfoDto(User user) {
        this.username = user.getEmail();
        this.role = user.getRole();
        this.timestamp = new Date().getTime();
    }
}
