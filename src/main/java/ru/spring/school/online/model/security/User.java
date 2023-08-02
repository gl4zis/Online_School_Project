package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity(name = "usr")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class User implements UserDetails {

    @Id
    protected final String username;
    protected final String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)
    protected final Set<Role> roles;
    protected boolean confirmed = true; //Will be realised soon
    protected String firstname;
    protected String lastname;
    protected Date dateOfBirth;
    protected String photoURL;  //Will be realised in future
    protected String description;
    protected Long phoneNumber;
    protected String email;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (confirmed) {
            if (roles == null)
                return Collections.singleton(Role.UNCONFIRMED.authority);
            Set<GrantedAuthority> authorities = new HashSet<>();
            for (Role role : roles)
                authorities.add(role.authority);
            return authorities;
        } else
            return Collections.singletonList(Role.UNCONFIRMED.authority);
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
        UNCONFIRMED(new SimpleGrantedAuthority("ROLE_UNCONFIRMED")),
        ADMIN(new SimpleGrantedAuthority("ROLE_ADMIN")),
        TEACHER(new SimpleGrantedAuthority("ROLE_TEACHER")),
        STUDENT(new SimpleGrantedAuthority("ROLE_STUDENT"));

        private final GrantedAuthority authority;
    }
}
