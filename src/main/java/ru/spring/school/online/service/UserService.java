package ru.spring.school.online.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;

import java.util.Optional;

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

    public Optional<User> findByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public boolean isUsernameUnique(User user) {
        Optional<User> userFormDB = userRepo.findById(user.getUsername());
        return userFormDB.isEmpty();
    }

    public boolean isEmailUnique(User user) {
        Optional<User> userFormDB = userRepo.findUserByEmail(user.getEmail());
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

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * false if there are error!
     */
    public boolean setUsernameUnique(User user, Errors errors, Model model) {
        if (errors.hasErrors())
            return false;
        if (!user.getUsername().equals(user.getOldUsername()) && !isUsernameUnique(user)) {
            model.addAttribute("usernameUnique", "This username is taken");
            return false;
        }
        return true;
    }

    /**
     * false if there are error!
     */
    public boolean setEmailUnique(User user, Errors errors, Model model) {
        if (errors.hasErrors())
            return false;
        if (!user.getEmail().equals(user.getOldEmail()) && !isEmailUnique(user)) {
            model.addAttribute("emailUnique", "This email is taken");
            return false;
        }
        return true;
    }

    /**
     * false if there are error!
     */
    public boolean setNewPassword(String oldPassword, String newPassword, String passwordConfirm,
                                  User user, Model model
    ) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("oldPasswordIncorrect", "Wrong password");
            return false;
        }
        if (newPassword == null || newPassword.length() < 6) {
            model.addAttribute("passwordException", "Password should be longer than 5 characters");
            return false;
        }
        if (!newPassword.equals(passwordConfirm)) {
            model.addAttribute("passwordEqualsException", "Passwords should be equals");
            return false;
        }
        return true;
    }
}
