package ru.spring.school.online.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.SubjectRepository;
import ru.spring.school.online.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    final UserRepository userRepo;
    final SubjectRepository subjectRepo;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, SubjectRepository subjectRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.subjectRepo = subjectRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findById(username);
        if (user.isPresent())
            return user.get();
        else
            throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    public Iterable<User> allUsers() {
        return userRepo.findAll();
    }

    public boolean isUsernameUnique(User user) {
        Optional<User> userFormDB = userRepo.findById(user.getUsername());
        return userFormDB.isEmpty();
    }

    public void saveUser(User user, boolean needsEncoding) {
        if (needsEncoding) {
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepo.save(user);
            user.setPassword(password);
        } else
            userRepo.save(user);
    }

    public boolean updateUser(User user) {
        if (isUsernameUnique(user))
            return false;
        saveUser(user, false);
        return true;
    }

    public boolean deleteUser(String username) {
        if (userRepo.findById(username).isPresent()) {
            userRepo.deleteById(username);
            return true;
        }
        return false;
    }
}
