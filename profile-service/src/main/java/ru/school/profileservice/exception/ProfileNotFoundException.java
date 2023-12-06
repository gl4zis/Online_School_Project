package ru.school.profileservice.exception;

public class ProfileNotFoundException extends Exception {
    public ProfileNotFoundException() {
        super("Profile not found");
    }
}
