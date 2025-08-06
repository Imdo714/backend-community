package com.back_community.global.exception.handleException;

public class S3UploadFailException extends RuntimeException {
    public S3UploadFailException(String message){
        super(message);
    }
}
