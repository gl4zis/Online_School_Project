package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity(name = "usr")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @NotBlank(message = "Username shouldn't be empty")
    @Size(min = 3, max = 30, message = "Username size must be between 3 and 30 characters")
    protected String username;
    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 6, message = "Password should be longer than 5 characters")
    protected String password;
    @Transient
    protected String passwordConfirm;
    @Enumerated(value = EnumType.STRING)
    protected Role role;
    protected boolean confirmed = true; //Will be realised soon
    protected String firstname;
    protected String lastname;
    protected String patronymic;
    protected Date dateOfBirth;
    protected int grade;
    @Column(unique = true)
    protected String email;
    protected String photoURL; //Will be realised in future
    protected Long phoneNumber;
    protected String description;
    @Enumerated(value = EnumType.STRING)
    @ManyToMany(fetch = FetchType.EAGER)
    protected Set<Subject> subjects;
    protected String education;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    protected Set<String> diplomas;
    protected int seniority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (confirmed) {
            if (role == null){
                return Collections.singletonList(Unconfirmed.AUTHORITY);
            }
            return Collections.singletonList(role.authority);
        } else
            return Collections.singletonList(Unconfirmed.AUTHORITY);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    private static class Unconfirmed{
        private static final GrantedAuthority AUTHORITY = new SimpleGrantedAuthority("ROLE_UNCONFIRMED");
    }

    @RequiredArgsConstructor
    public enum Role {
        ADMIN(new SimpleGrantedAuthority("ROLE_ADMIN")),
        TEACHER(new SimpleGrantedAuthority("ROLE_TEACHER")),
        STUDENT(new SimpleGrantedAuthority("ROLE_STUDENT"));

        private final GrantedAuthority authority;
    }
    @AssertTrue(message = "Passwords should be equals")
    public boolean isPasswordsEquals() {
        return passwordConfirm == null || password.equals(passwordConfirm);
    }
}
