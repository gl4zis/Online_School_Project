package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    @Id
    protected String username;  //*
    protected String password;  //*

    protected String firstname; //Student|Teacher *
    protected String lastname; //Student|Teacher *
    protected String middleName;

    @Temporal(TemporalType.DATE)
    protected Date dateOfBirth; //Student *

    @Enumerated(value = EnumType.STRING)
    protected Role role;  //*

    @Column(unique = true)
    protected String email;

    @Column(columnDefinition = "TEXT")
    protected String photoBase64;
    private boolean locked;

    // Only one role is possible
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @RequiredArgsConstructor
    public enum Role {
        ADMIN,
        TEACHER,
        UNCONFIRMED_TEACHER,
        STUDENT;
    }
}