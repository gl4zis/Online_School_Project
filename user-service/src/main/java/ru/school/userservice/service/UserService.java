package ru.school.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.school.exception.InvalidTokenException;
import ru.school.userservice.model.User;
import ru.school.userservice.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }

    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }

    public User getByRefresh(String refresh) throws InvalidTokenException {
        return userRepository.getByRefreshToken(refresh)
                .orElseThrow(InvalidTokenException::new);
    }

    public void removeUser(Long accountId) {
        userRepository.deleteById(accountId);
    }

    public User getById(Long accountId) {
        return userRepository.findById(accountId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id '" + accountId + "' not found"));
    }

    public Iterable<User> getTeachersAccounts() {
        return userRepository.getAllByRole(User.Role.TEACHER);
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public void setLock(Long userId, boolean lock) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with id '" + userId + "' not found");

        user.get().setLocked(lock);
        if (lock)
            user.get().setRefreshToken(null);

        userRepository.save(user.get());
    }

    public void setConfirm(Long userId, boolean confirm) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with id '" + userId + "' not found");

        user.get().setConfirmed(confirm);
        userRepository.save(user.get());
    }
}
