package com.back_community.global.exception.handleException;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }
}
