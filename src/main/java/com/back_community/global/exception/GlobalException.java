package com.back_community.global.exception;

import com.back_community.api.ApiResponse;
import com.back_community.global.exception.handleException.DuplicateEmailException;
import com.back_community.global.exception.handleException.MismatchException;
import com.back_community.global.exception.handleException.NotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }

    @ExceptionHandler(MismatchException.class)
    public ApiResponse<Object> handleMismatchException(MismatchException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return ApiResponse.of(HttpStatus.FORBIDDEN, e.getMessage(), null);
    }

}
