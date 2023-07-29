package ru.spring.school.online.model.security;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity(name = "usr")
@Data
@RequiredArgsConstructor
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected final String username;
    protected final String password;

    protected String firstname;
    protected String lastname;
    protected int age;
    protected int classNumber;
    protected String photo;  //Will be realised in future
    protected String description;
    protected long phoneNumber;
    protected final String email;

    protected final Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role.authority);
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


    @RequiredArgsConstructor
    protected enum Role {
        ADMIN(new SimpleGrantedAuthority("ADMIN")),
        TEACHER(new SimpleGrantedAuthority("TEACHER")),
        STUDENT(new SimpleGrantedAuthority("STUDENT"));

        private final GrantedAuthority authority;
    }
}
