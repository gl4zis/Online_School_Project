package ru.school.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + email + "' not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
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

    public Iterable<User> getByRole(User.Role role) {
        return userRepository.getAllByRole(role);
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public void setLock(Long userId, boolean lock) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with id '" + userId + "' not found");

        user.get().setLocked(lock);
        if (lock) {
            user.get().setRefreshToken(null);
            user.get().setPublished(false);
        }

        userRepository.save(user.get());
    }

    public void setPublic(Long userId, boolean published) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User with id '" + userId + "' not found");

        if (user.get().isLocked())
            throw new LockedException("Locked user can't be published");

        user.get().setPublished(published);
        userRepository.save(user.get());
    }
}
