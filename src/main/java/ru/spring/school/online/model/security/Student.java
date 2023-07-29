package ru.spring.school.online.model.security;

public class Student extends User {

    public Student(String username, String password, String email,
                   String firstName, String lastName, int age,
                   int classNumber) {
        super(username, password, email, Role.STUDENT);
        this.firstname = firstName;
        this.lastname = lastName;
        this.age = age;
        this.classNumber = classNumber;
    }
}
