package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;
import ru.spring.school.online.utils.DtoMappingUtils;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final DtoMappingUtils dtoMappingUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    public boolean isUsernameUnique(String username) {
        return !userRepo.existsById(username);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(String username) {
        userRepo.deleteById(username);
    }

    public void updateProfile(String oldUsername, ProfileUpdateDto update)
            throws UsernameNotFoundException, UsernameIsTakenException {
        if (!update.getUsername().equals(oldUsername) && !isUsernameUnique(update.getUsername()))
            throw new UsernameIsTakenException(update.getUsername());

        User user = (User) loadUserByUsername(oldUsername);
        dtoMappingUtils.updatedUser(user, update);
        userRepo.save(user);
    }
}
