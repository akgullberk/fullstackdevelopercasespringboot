package com.akgulberk.fullstackdevelopercasespringboot.controller;

import com.akgulberk.fullstackdevelopercasespringboot.dto.UserProfileDTO;
import com.akgulberk.fullstackdevelopercasespringboot.service.FileStorageService;
import com.akgulberk.fullstackdevelopercasespringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;
    private final UserService userService;

    @PostMapping("/upload/profile-photo")
    public ResponseEntity<?> uploadProfilePhoto(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Lütfen bir dosya seçin");
        }

        try {
            // Dosyayı kaydet
            String fileUrl = fileStorageService.storeFile(file);
            
            // Kullanıcı profilini güncelle
            UserProfileDTO updatedProfile = userService.updateProfilePhoto(userDetails.getUsername(), fileUrl);
            
            Map<String, Object> response = new HashMap<>();
            response.put("fileUrl", fileUrl);
            response.put("message", "Profil fotoğrafı başarıyla yüklendi");
            response.put("user", updatedProfile);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Dosya yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/profile-photo")
    public ResponseEntity<?> deleteProfilePhoto(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        try {
            // Kullanıcı profilini güncelle (fotoğrafı null yap)
            UserProfileDTO updatedProfile = userService.updateProfilePhoto(userDetails.getUsername(), null);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Profil fotoğrafı başarıyla silindi");
            response.put("user", updatedProfile);
            
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Dosya silinirken bir hata oluştu: " + ex.getMessage());
        }
    }
} 