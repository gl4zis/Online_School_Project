package ru.spring.school.online.model.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
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
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

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
    protected boolean gotUsername = false;
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

    @AssertTrue(message = "Passwords should be equals")
    public boolean isPasswordsEquals() {
        return passwordConfirm == null || password.equals(passwordConfirm);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (confirmed) {
            if (role == null) {
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

    @RequiredArgsConstructor
    public enum Role {
        ADMIN(new SimpleGrantedAuthority("ROLE_ADMIN")),
        TEACHER(new SimpleGrantedAuthority("ROLE_TEACHER")),
        STUDENT(new SimpleGrantedAuthority("ROLE_STUDENT"));

        private final GrantedAuthority authority;
    }

    private static class Unconfirmed {
        private static final GrantedAuthority AUTHORITY = new SimpleGrantedAuthority("ROLE_UNCONFIRMED");
    }

    @AssertTrue(message = "Firstname can't be blank")
    public boolean isFirstnameValid(){
        return !gotUsername || role == Role.ADMIN || (firstname!= null && !firstname.isBlank());
    }

    @AssertTrue(message = "Lastname can't be blank")
    public boolean isLastnameValid(){
        return !gotUsername || role == Role.ADMIN || (lastname!= null && !lastname.isBlank());
    }

    @AssertTrue(message = "Patronymic can't be blank")
    public boolean isPatronymicValid(){
        return patronymic == null || !patronymic.isBlank();
    }

    @AssertTrue(message = "Birth date should be in the past")
    public boolean isBirthDateValid(){
        return !gotUsername  || role == Role.ADMIN || (dateOfBirth!= null && !(dateOfBirth.compareTo(new Date()) > 0));
    }

    @AssertTrue(message = "Grade should be between 9 and 11")
    public boolean isGradeValid(){
        return !gotUsername || role != Role.STUDENT || (grade >= 9 && grade <= 11);
    }

    @AssertTrue(message = "Email should be correct.")
    public boolean isEmailValid(){
        return !gotUsername || role == Role.ADMIN || (email != null && Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", email));
    }

    @AssertTrue(message = "Phone should be correct.")
    public boolean isPhoneValid(){
        return phoneNumber == null || Pattern.matches("^\\d{11}$", phoneNumber.toString());
    }

    @AssertTrue(message = "Description can't be blank")
    public boolean isDescriptionValid(){
        return !gotUsername || role != Role.TEACHER || (description!= null && !description.isBlank());
    }

    @AssertTrue(message = "There should be between 1 and 3 subjects")
    public boolean isSubjectsValid(){
        return !gotUsername || role != Role.TEACHER || (subjects!= null && subjects.size() >= 1 && subjects.size() <= 3);
    }

    @AssertTrue(message = "Education can't be blank")
    public boolean isEducationValid(){
        return !gotUsername || role != Role.TEACHER || (education!= null && !education.isBlank());
    }

    @AssertTrue(message = "seniority can't be negative")
    public boolean isSeniorityValid(){
        return !gotUsername || role != Role.TEACHER || seniority >= 0;
    }


}

