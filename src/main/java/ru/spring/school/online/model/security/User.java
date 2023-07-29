package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity(name = "usr")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class User implements UserDetails {

    @Id
    protected final String username;
    protected final String password;

    protected String firstname;
    protected String lastname;
    protected Integer age;
    protected Integer classNumber;
    protected String photo;  //Will be realised in future
    protected String description;
    protected Long phoneNumber;
    protected final String email;

    @Enumerated(value = EnumType.STRING)
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

        public static Role getByName(String roleName) {
            for (Role role : Role.values()) {
                if (role.name().equals(roleName))
                    return role;
            }
            return null;
        }
    }
}