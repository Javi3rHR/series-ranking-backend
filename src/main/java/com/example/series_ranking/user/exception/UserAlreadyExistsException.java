package com.example.series_ranking.user.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String username, String email) {
        super(String.format("User with username '%s' or email '%s' already exists", username, email));
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}