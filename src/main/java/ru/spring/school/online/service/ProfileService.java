package ru.spring.school.online.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.utils.DtoMappingUtils;
import ru.spring.school.online.utils.ResponseUtils;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserService userService;
    private final DtoMappingUtils dtoMappingUtils;
    private final ResponseUtils responseUtils;

    public ResponseEntity<?> getProfile(HttpServletRequest request, String username) {
        try {
            User user = (User) userService.loadUserByUsername(username);
            return ResponseEntity.ok(dtoMappingUtils.profileFromUser(user));
        } catch (UsernameNotFoundException e) {
            return responseUtils.returnError(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    request.getServletPath()
            );
        }
    }
}
