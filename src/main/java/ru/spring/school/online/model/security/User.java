package ru.spring.school.online.model.security;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class User implements UserDetails {

    @Id
    protected final String username;
    protected final String password;
    @Enumerated(value = EnumType.STRING)
    protected final Role role;
    protected String firstname;
    protected String lastname;
    protected Integer age;
    protected Integer classNumber;
    protected String photo;  //Will be realised in future
    protected String description;
    protected Long phoneNumber;
    protected String email;

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
    public enum Role {
        ADMIN(new SimpleGrantedAuthority( "ROLE_ADMIN")),
        TEACHER(new SimpleGrantedAuthority("ROLE_TEACHER")),
        STUDENT(new SimpleGrantedAuthority("ROLE_STUDENT"));

        private final GrantedAuthority authority;
    }
}
