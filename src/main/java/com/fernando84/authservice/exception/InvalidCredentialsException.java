package com.fernando84.authservice.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("User or Password is incorrect");
    }
}