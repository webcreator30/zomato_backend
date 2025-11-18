package com.example.zomato.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String getRootCauseMessage(Throwable ex) {
        Throwable root = ex;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root.getMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("error", ex.getMessage());
        res.put("cause", getRootCauseMessage(ex)); // 🔥 SHOW REAL REASON
        res.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(res, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("timestamp", LocalDateTime.now());
        res.put("error", ex.getMessage());
        res.put("cause", getRootCauseMessage(ex));
        res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
