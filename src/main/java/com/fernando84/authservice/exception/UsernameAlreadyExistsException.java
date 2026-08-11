package com.fernando84.authservice.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("User '" + username + "' already exists");
    }
}