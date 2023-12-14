package ru.school.authservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.school.authservice.dto.AuthRequest;
import ru.school.authservice.dto.AuthWithRoleRequest;
import ru.school.authservice.dto.PublicAccountInfo;
import ru.school.authservice.model.Account;

import java.util.Set;
import java.util.stream.Collectors;

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

    public Account createNewAccount(AuthWithRoleRequest request) {
        return Account
                .builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .locked(false)
                .roles(request.getRoles())
                .build();
    }

    public PublicAccountInfo getAccountInfo(Account account) {
        return PublicAccountInfo.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .roles(account.getRoles().stream().map(Object::toString).collect(Collectors.toSet()))
                .locked(account.isLocked())
                .build();
    }
}
