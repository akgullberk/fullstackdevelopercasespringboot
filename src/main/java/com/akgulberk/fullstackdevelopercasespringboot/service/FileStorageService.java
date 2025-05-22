package com.akgulberk.fullstackdevelopercasespringboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        try {
            // Dosya adını normalize et
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            
            // Dosya uzantısını kontrol et
            String fileExtension = getFileExtension(fileName);
            if (!isValidImageExtension(fileExtension)) {
                throw new IllegalArgumentException("Sadece JPG, JPEG, PNG ve GIF dosyaları yüklenebilir");
            }

            // Benzersiz dosya adı oluştur
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            
            // Upload dizinini oluştur
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Dosyayı kaydet
            Path targetLocation = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // URL'yi döndür
            return "/uploads/" + uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Dosya yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private boolean isValidImageExtension(String extension) {
        return extension.equals(".jpg") || 
               extension.equals(".jpeg") || 
               extension.equals(".png") || 
               extension.equals(".gif");
    }

    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
                String fileName = fileUrl.substring("/uploads/".length());
                Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
                Files.deleteIfExists(filePath);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Dosya silinirken bir hata oluştu: " + ex.getMessage());
        }
    }
} 