package ru.school.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.school.exception.InvalidTokenException;
import ru.school.userservice.model.User;
import ru.school.userservice.repository.UserRepository;

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
}