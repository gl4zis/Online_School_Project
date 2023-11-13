package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.spring.school.online.model.UserFile;

import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "users")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    @Id
    protected String username;  //*
    protected String password;  //*
    @Column(unique = true)
    protected String email;

    protected String firstname; //Student|Teacher *
    protected String lastname; //Student|Teacher *
    protected String middleName;

    @Temporal(TemporalType.DATE)
    protected Date birthdate; //Student *

    @ElementCollection
    @Enumerated(EnumType.STRING)
    protected Set<Role> roles;  //*

    @OneToOne
    protected UserFile photo;
    private boolean locked;
    @Column(unique = true)
    private String refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public void setRoles(Set<Role> newRoles) {
        roles = newRoles;
    }

    public void setRoles(Role... newRoles) {
        roles = new HashSet<>();
        roles.addAll(Arrays.asList(newRoles));
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

    public enum Role {
        ADMIN,
        TEACHER,
        STUDENT
    }
}