package com.akgulberk.fullstackdevelopercasespringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "digital_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Column
    private String profilePhotoUrl;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @ElementCollection
    @CollectionTable(name = "digital_card_social_media", joinColumns = @JoinColumn(name = "digital_card_id"))
    private List<SocialMediaLink> socialMediaLinks = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "digital_card_skills", joinColumns = @JoinColumn(name = "digital_card_id"))
    private List<String> skills = new ArrayList<>();

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialMediaLink {
        @Column(nullable = false)
        private String platform; // GITHUB, LINKEDIN, INSTAGRAM, TWITTER, OTHER

        @Column(nullable = false)
        private String url;

        @Column
        private String customName; // Diğer sosyal medya platformları için özel isim
    }
} 