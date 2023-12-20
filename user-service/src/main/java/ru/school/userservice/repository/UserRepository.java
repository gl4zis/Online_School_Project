package ru.school.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.school.userservice.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> getByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Optional<User> getByRefreshToken(String refreshToken);
}
