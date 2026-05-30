package com.example.ics.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UsernameNotFoundException e) {
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException e) {
        return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String,String>> handleAccessDenied(AccessDeniedException e){
        return ResponseEntity.status(401).body(Map.of("error","Access Denied"));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrity(DataIntegrityViolationException e){
        String message = e.getMostSpecificCause().getMessage();

        if (message.contains("product_sku_key")) {
            return ResponseEntity.status(409).body(Map.of("error", "SKU already exists"));
        }
        if (message.contains("users_email_key")) {
            return ResponseEntity.status(409).body(Map.of("error", "Email already registered"));
        }
        if (message.contains("product_warehouse_pkey")) {
            return ResponseEntity.status(409).body(Map.of("error", "Stock record already exists for this product and warehouse"));
        }
        if (message.contains("stock_movements_warehouse_id_fkey")) {
            return ResponseEntity.status(409).body(Map.of("error", "Cannot delete warehouse with existing stock history"));
        }
        return  ResponseEntity.status(409).body(Map.of("error","Data Conflict"));
    }
}