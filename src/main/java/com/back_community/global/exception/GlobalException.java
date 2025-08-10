package com.back_community.global.exception;

import com.back_community.api.ApiResponse;
import com.back_community.global.exception.handleException.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Object> handleNotFoundException(NotFoundException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return ApiResponse.of(HttpStatus.NOT_FOUND, e.getMessage(), null);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ApiResponse<Object> handleDuplicateEmailException(DuplicateEmailException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.CONFLICT.value());
        return ApiResponse.of(HttpStatus.CONFLICT, e.getMessage(), null);
    }

    @ExceptionHandler(MismatchException.class)
    public ApiResponse<Object> handleMismatchException(MismatchException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return ApiResponse.of(HttpStatus.FORBIDDEN, e.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletResponse response) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .sorted(Comparator.comparing(fieldError -> fieldError.getCode().equals("NotNull") ? 0 : 1))
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("잘못된 요청입니다.");

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiResponse.of(HttpStatus.BAD_REQUEST, errorMessage, null);
    }

    @ExceptionHandler(S3UploadFailException.class)
    public ApiResponse<Object> handleS3UploadFailException(S3UploadFailException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.LOCKED.value());
        return ApiResponse.of(HttpStatus.LOCKED, e.getMessage(), null);
    }

    @ExceptionHandler(MatchException.class)
    public ApiResponse<Object> matchExceptionException(MatchException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.CONFLICT.value());
        return ApiResponse.of(HttpStatus.CONFLICT, e.getMessage(), null);
    }
}
