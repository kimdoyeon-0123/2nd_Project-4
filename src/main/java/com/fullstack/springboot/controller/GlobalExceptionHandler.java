package com.fullstack.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        // 403 Forbidden 오류가 발생한 경우
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body("접근이 거부되었습니다: " + e.getMessage());
    }

    // 다른 예외를 처리하는 핸들러도 추가할 수 있습니다.
}
