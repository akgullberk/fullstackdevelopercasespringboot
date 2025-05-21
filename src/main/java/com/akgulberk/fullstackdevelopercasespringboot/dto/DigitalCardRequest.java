package com.akgulberk.fullstackdevelopercasespringboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class DigitalCardRequest {
    @NotBlank(message = "Ad soyad alanı boş olamaz")
    @Size(min = 2, max = 100, message = "Ad soyad 2-100 karakter arasında olmalıdır")
    private String fullName;

    private String profilePhotoUrl;

    @Size(max = 100, message = "Ünvan en fazla 100 karakter olabilir")
    private String title;

    @Size(max = 1000, message = "Biyografi en fazla 1000 karakter olabilir")
    private String biography;

    private List<SocialMediaLinkRequest> socialMediaLinks;

    private List<@Size(min = 2, max = 11, message = "Yetenek adı 2-11 karakter arasında olmalıdır") String> skills;

    @Data
    public static class SocialMediaLinkRequest {
        @NotBlank(message = "Platform adı boş olamaz")
        private String platform;

        @NotBlank(message = "URL boş olamaz")
        @Size(max = 500, message = "URL en fazla 500 karakter olabilir")
        private String url;

        @Size(max = 100, message = "Özel isim en fazla 100 karakter olabilir")
        private String customName;
    }
} 