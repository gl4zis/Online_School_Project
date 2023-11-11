package ru.spring.school.online.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spring.school.online.dto.request.ProfileUpdateDto;
import ru.spring.school.online.dto.response.ProfileInfo;
import ru.spring.school.online.exception.EmailIsTakenException;
import ru.spring.school.online.exception.UsernameIsTakenException;
import ru.spring.school.online.model.UserFile;
import ru.spring.school.online.model.security.Teacher;
import ru.spring.school.online.model.security.User;
import ru.spring.school.online.repository.UserRepository;
import ru.spring.school.online.utils.DtoMappingUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final DtoMappingUtils dtoMappingUtils;
    private final FileService fileService;

    /**
     * Loads user by username or email (it is unique)
     *
     * @param username the username|email identifying the user whose data is required.
     * @return User, you can always cast this method result to (User)
     * @throws UsernameNotFoundException if there are no such user in DB
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findById(username)
                .orElseGet(() -> userRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found")));
    }

    public User getOnlyByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
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

    public void updateProfile(String oldUsername, ProfileUpdateDto update)
            throws UsernameNotFoundException, UsernameIsTakenException, EmailIsTakenException {
        User user = (User) loadUserByUsername(oldUsername);
        if (!update.getUsername().equals(oldUsername) && !isUsernameUnique(update.getUsername()))
            throw new UsernameIsTakenException(update.getUsername());

        if (update.getEmail() != null &&
                !update.getEmail().equals(user.getEmail()) &&
                !isEmailUnique(update.getEmail())) {
            throw new EmailIsTakenException(update.getEmail());
        }

        user = dtoMappingUtils.updateUser(user, update);
        userRepo.save(user);
    }

    public Set<ProfileInfo> getAll() {
        Iterable<User> users = userRepo.findAll();
        Set<ProfileInfo> usersSet = new HashSet<>();
        users.forEach(user -> usersSet.add(dtoMappingUtils.profileFromUser(user)));
        return usersSet;
    }

    public void changeUserLock(String username, boolean lock) throws AccessDeniedException {
        User user = getOnlyByUsername(username);
        if (user.hasRole(User.Role.ADMIN))
            throw new AccessDeniedException("Admin account can't be locked");

        user.setLocked(lock);
        userRepo.save(user);
    }

    public String getPhotoBase64(String username) throws IOException {
        UserFile photo = getOnlyByUsername(username).getPhoto();
        if (photo == null)
            throw new FileNotFoundException("photo");
        return fileService.getFileBased64(photo.getKey());
    }

    public void updatePhoto(String username, UserFile photo) throws UsernameNotFoundException, AccessDeniedException {
        User user = (User) loadUserByUsername(username);

        if (user instanceof Teacher teacher) {
            if (photo == null)
                throw new AccessDeniedException("Teacher have to has photo");
            else
                teacher.setConfirmed(true);
        }

        UserFile oldPhoto = user.getPhoto();
        user.setPhoto(photo);
        userRepo.save(user);
        if (oldPhoto != null)
            fileService.removeFile(oldPhoto);
    }

    public void updateDiploma(String username, UserFile file) throws UsernameNotFoundException {
        User user = (User) loadUserByUsername(username);

        if (user instanceof Teacher teacher) {

            if (teacher.getDiploma() != null)
                fileService.removeFile(teacher.getDiploma());

            teacher.setDiploma(file);
            userRepo.save(teacher);
        } else
            throw new AccessDeniedException("Only for teachers");
    }
}
