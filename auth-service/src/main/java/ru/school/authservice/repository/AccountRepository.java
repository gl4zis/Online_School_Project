package ru.school.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.school.authservice.model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> getByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    Optional<Account> getByRefreshToken(String token);
}
