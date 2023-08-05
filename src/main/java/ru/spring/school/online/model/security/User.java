package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity(name = "usr")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
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
    @Column(nullable = false, unique = true)
    @Email(message = "Input correct email")
    protected String email;

    @AssertTrue(message = "Passwords should be equals")
    public boolean isPasswordsEquals() {
        return passwordConfirm == null || password.equals(passwordConfirm);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role.authority);
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

    public Student toStudent() {
        return new Student(this);
    }

    public Teacher toTeacher() {
        return new Teacher(this);
    }

    @RequiredArgsConstructor
    public enum Role {
        ADMIN(new SimpleGrantedAuthority("ROLE_ADMIN")),
        TEACHER(new SimpleGrantedAuthority("ROLE_TEACHER")),
        UNCONFIRMED_TEACHER(new SimpleGrantedAuthority("ROLE_UNCONFIRMED_TEACHER")),
        STUDENT(new SimpleGrantedAuthority("ROLE_STUDENT"));

        private final GrantedAuthority authority;
    }
}