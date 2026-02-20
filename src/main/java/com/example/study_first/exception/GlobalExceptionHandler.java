package com.example.study_first.exception;

import com.example.study_first.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException이 발생하면 이 메서드가 실행됩니다.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());

        // 404 Not Found 상태 코드와 함께 에러 메시지를 반환합니다.
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}