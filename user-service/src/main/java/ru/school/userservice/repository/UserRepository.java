package ru.school.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.school.userservice.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> getByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Optional<User> getByRefreshToken(String refreshToken);
    Iterable<User> getAllByRole(User.Role role);
}
