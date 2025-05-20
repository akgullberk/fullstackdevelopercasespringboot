package com.akgulberk.fullstackdevelopercasespringboot.dto;

import lombok.Data;

import java.util.List;

@Data
public class DigitalCardResponse {
    private Long id;
    private String fullName;
    private String profilePhotoUrl;
    private String title;
    private String biography;
    private List<SocialMediaLinkResponse> socialMediaLinks;
    private List<String> skills;
    private String username; // Kullanıcının username'i

    @Data
    public static class SocialMediaLinkResponse {
        private String platform;
        private String url;
        private String customName;
    }
} 