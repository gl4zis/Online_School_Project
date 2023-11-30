package ru.school.authservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.school.authservice.dto.request.AuthRequest;
import ru.school.authservice.model.Account;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DtoMapper {
    private final PasswordEncoder encoder;

    public Account createNewStudent(AuthRequest request) {
        return Account
                .builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .locked(false)
                .roles(Set.of(Account.Role.STUDENT))
                .build();
    }
}
