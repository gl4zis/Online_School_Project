package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findById(username).orElseGet(() -> userRepo.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found")));
    }

    public Iterable<User> allUsers() {
        return userRepo.findAll();
    }

    public boolean isUsernameUnique(String username) {
        return !userRepo.existsById(username);
    }

    public boolean isEmailUnique(String email) {
        return !userRepo.existsByEmail(email);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(String username) {
        userRepo.deleteById(username);
    }
}
