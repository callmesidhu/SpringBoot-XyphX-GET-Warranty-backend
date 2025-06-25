package com.xyphx.getwarranty.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
                Map<String, String> res = new HashMap<>();
                res.put("message", ex.getMessage());
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
}
