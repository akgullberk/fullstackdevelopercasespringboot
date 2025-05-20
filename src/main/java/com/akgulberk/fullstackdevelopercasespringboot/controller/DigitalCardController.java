package com.akgulberk.fullstackdevelopercasespringboot.controller;

import com.akgulberk.fullstackdevelopercasespringboot.dto.DigitalCardRequest;
import com.akgulberk.fullstackdevelopercasespringboot.dto.DigitalCardResponse;
import com.akgulberk.fullstackdevelopercasespringboot.service.DigitalCardService;
import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/digital-cards")
@RequiredArgsConstructor
public class DigitalCardController {

    private final DigitalCardService digitalCardService;

    @PostMapping
    public ResponseEntity<DigitalCardResponse> createDigitalCard(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody DigitalCardRequest request) {
        return ResponseEntity.ok(digitalCardService.createDigitalCard(userDetails.getUsername(), request));
    }

    @GetMapping("/{username}")
    public ResponseEntity<DigitalCardResponse> getDigitalCard(@PathVariable String username) {
        return ResponseEntity.ok(digitalCardService.getDigitalCard(username));
    }

    @PutMapping
    public ResponseEntity<DigitalCardResponse> updateDigitalCard(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody DigitalCardRequest request) {
        return ResponseEntity.ok(digitalCardService.updateDigitalCard(userDetails.getUsername(), request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDigitalCard(@AuthenticationPrincipal UserDetails userDetails) {
        digitalCardService.deleteDigitalCard(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
} 