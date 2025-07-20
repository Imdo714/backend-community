package com.back_community.global.exception.handleException;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message){
        super(message);
    }
}
