package ru.school.userservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.school.exception.InvalidTokenException;
import ru.school.userservice.model.User;
import ru.school.userservice.repository.UserRepository;
import ru.school.userservice.service.UserService;

import java.util.Optional;

public class UserServiceTest {
    final static String RIGHT_EMAIL = "existing@email.com";
    final static String WRONG_EMAIL = "nosuch@email.com";
    final static String RIGHT_TOKEN = "right_token";
    final static String WRONG_TOKEN = "wrong_token";

    static UserRepository userRepo;
    static UserService userService;

    @BeforeAll
    static void init() {
        userRepo = Mockito.mock(UserRepository.class);
        Mockito.when(userRepo.getByEmail(RIGHT_EMAIL)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepo.getByEmail(WRONG_EMAIL)).thenReturn(Optional.empty());
        Mockito.when(userRepo.getByRefreshToken(RIGHT_TOKEN)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepo.getByRefreshToken(WRONG_TOKEN)).thenReturn(Optional.empty());
        userService = new UserService(userRepo);
    }

    @Test
    void loadUserByValidEmail() {
        Assertions.assertSame(userService.loadUserByUsername(RIGHT_EMAIL).getClass(), User.class);
    }

    @Test
    void loadNotExistingEmail() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(WRONG_EMAIL));
    }

    @Test
    void getByValidToken() {
        try {
            Assertions.assertSame(userService.getByRefresh(RIGHT_TOKEN).getClass(), User.class);
        } catch (InvalidTokenException e) {
            Assertions.fail();
        }
    }

    @Test
    void getByWrongToken() {
        Assertions.assertThrows(InvalidTokenException.class, () -> userService.getByRefresh(WRONG_TOKEN));
    }
}
