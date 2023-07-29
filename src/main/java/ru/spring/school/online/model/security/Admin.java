package ru.spring.school.online.model.security;

public class Admin extends User {

    public Admin(String username, String password, String email) {
        super(username, password, email, Role.ADMIN);
    }
}
