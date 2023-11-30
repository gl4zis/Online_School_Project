package ru.school.authservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
public class Account implements UserDetails {
    @Id
    protected String username;  //*
    @Column(nullable = false)
    protected String password;  //*
    @Column(unique = true)
    protected String email;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Set<Role> roles;  //*
    @Column(nullable = false)
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