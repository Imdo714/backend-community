package com.back_community.global.exception.handleException;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message){
        super(message);
    }
}
