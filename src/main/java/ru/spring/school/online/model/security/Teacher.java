package ru.spring.school.online.model.security;

public class Teacher extends User {

    public Teacher(String username, String password, String email,
                   String firstName, String lastName,
                   String description, String photo) {
        super(username, password,  email, Role.TEACHER);
        this.firstname = firstName;
        this.lastname = lastName;
        this.description = description;
        this.photo = photo;
    }
}
