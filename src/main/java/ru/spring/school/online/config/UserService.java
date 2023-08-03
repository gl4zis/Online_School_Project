package ru.spring.school.online.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.spring.school.online.model.security.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spring.school.online.repository.SubjectRepository;
import ru.spring.school.online.repository.UserRepository;

import java.util.List;
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

    public Iterable<User> allUsers(){
        return userRepo.findAll();
    }

    public boolean registerUser(User user) {
        Optional<User> userFormDB = userRepo.findById(user.getUsername());
        if (userFormDB.isPresent()){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return true;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public boolean deleteUser(String username){
        if (userRepo.findById(username).isPresent()){
            userRepo.deleteById(username);
            return true;
        }
        return false;
    }
}
