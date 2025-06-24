package com.xyphx.getwarranty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
                Map<String, String> response = new HashMap<>();
                response.put("error", ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
}
