package com.xyphx.getwarranty.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

        @RequestMapping("/error")
        public ResponseEntity<Map<String, Object>> handleError() {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("timestamp", LocalDateTime.now());
                errorResponse.put("status", 403);
                errorResponse.put("error", "Forbidden");
                errorResponse.put("message", "❌ Access denied or invalid endpoint");
                errorResponse.put("path", "/error");

                return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
        }
}
