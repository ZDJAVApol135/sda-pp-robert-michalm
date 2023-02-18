package com.sda.exeption;

public class UsernameConflictException extends RuntimeException{

    public UsernameConflictException(String message) {
        super(message);
    }
}