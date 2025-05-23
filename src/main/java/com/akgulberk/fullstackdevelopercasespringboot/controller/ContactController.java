package com.akgulberk.fullstackdevelopercasespringboot.controller;

import com.akgulberk.fullstackdevelopercasespringboot.dto.ContactFormRequest;
import com.akgulberk.fullstackdevelopercasespringboot.entity.User;
import com.akgulberk.fullstackdevelopercasespringboot.service.EmailService;
import com.akgulberk.fullstackdevelopercasespringboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/send")
    public ResponseEntity<String> sendContactForm(@Valid @RequestBody ContactFormRequest request) {
        try {
            User recipient = userService.findByUsername(request.getRecipientUsername());
            
            String messageBody = String.format("""
                Gönderen: %s
                E-posta: %s
                
                Mesaj:
                %s
                """, request.getName(), request.getEmail(), request.getMessage());

            emailService.sendEmail(recipient.getEmail(), request.getSubject(), messageBody);
            
            return ResponseEntity.ok("Mesajınız başarıyla gönderildi.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Mesaj gönderilirken bir hata oluştu: " + e.getMessage());
        }
    }

    @PostMapping("/test")
    public ResponseEntity<String> testEmail(@RequestParam String toEmail) {
        try {
            emailService.sendEmail(
                toEmail,
                "SendGrid Test E-postası",
                "Bu bir test e-postasıdır. SendGrid entegrasyonunu test etmek için gönderilmiştir."
            );
            return ResponseEntity.ok("Test e-postası başarıyla gönderildi.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Test e-postası gönderilirken hata oluştu: " + e.getMessage());
        }
    }
} 