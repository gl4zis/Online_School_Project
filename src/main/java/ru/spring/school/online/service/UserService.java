package ru.spring.school.online.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    final UserRepository userRepo;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findById(username);
        if (user.isPresent())
            return user.get();
        else {
            Optional<User> userByEmail = userRepo.findUserByEmail(username);
            if (userByEmail.isPresent())
                return userByEmail.get();
            else
                throw new UsernameNotFoundException("User '" + username + "' not found");
        }
    }

    public User findUser(String username) {
        Optional<User> userOpt = userRepo.findById(username);
        if (userOpt.isPresent())
            return userOpt.get();
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    public Iterable<User> allUsers() {
        return userRepo.findAll();
    }

    public boolean isUsernameUnique(String username) {
        Optional<User> userFormDB = userRepo.findById(username);
        return userFormDB.isEmpty();
    }

    public boolean isEmailUnique(String email) {
        Optional<User> userFormDB = userRepo.findUserByEmail(email);
        return userFormDB.isEmpty();
    }

    public void saveUser(User user, boolean needsEncoding) {
        if (needsEncoding) {
            String password = user.getPassword();
            user.setPassword(encodePassword(password));
            userRepo.save(user);
            user.setPassword(password);
        } else
            userRepo.save(user);
    }

    public boolean usernameValidate(String username) {
        if (username == null)
            return false;
        if (username.length() < 3 || username.length() > 30)
            return false;
        return isUsernameUnique(username);
    }

    public boolean emailValidate(String email) {
        Pattern emailRegex = Pattern.compile("^[\\w.%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        if (email == null)
            return false;
        if (!emailRegex.matcher(email).find())
            return false;
        return isEmailUnique(email);
    }

    public void updateUser(User user) {
        saveUser(user, false);
    }

    public boolean deleteUser(String username) {
        if (userRepo.findById(username).isPresent()) {
            userRepo.deleteById(username);
            return true;
        }
        return false;
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    public boolean checkOldPassword(String inputPassword, String currentPassword){
        return passwordEncoder.matches(inputPassword, currentPassword);
    }

    public boolean isPasswordValid(String password){
        return (password != null && password.length() >= 6);
    }

    public boolean isPasswordsEquals(String password, String passwordConfirm){
        return password.equals(passwordConfirm);
    }
}
