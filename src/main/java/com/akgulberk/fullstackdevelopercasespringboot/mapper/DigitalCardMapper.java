package com.akgulberk.fullstackdevelopercasespringboot.mapper;

import com.akgulberk.fullstackdevelopercasespringboot.dto.DigitalCardRequest;
import com.akgulberk.fullstackdevelopercasespringboot.dto.DigitalCardResponse;
import com.akgulberk.fullstackdevelopercasespringboot.entity.DigitalCard;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DigitalCardMapper {

    public DigitalCard toEntity(DigitalCardRequest request) {
        DigitalCard card = new DigitalCard();
        card.setFullName(request.getFullName());
        card.setProfilePhotoUrl(request.getProfilePhotoUrl());
        card.setTitle(request.getTitle());
        card.setBiography(request.getBiography());
        
        if (request.getSocialMediaLinks() != null) {
            card.setSocialMediaLinks(request.getSocialMediaLinks().stream()
                    .map(link -> new DigitalCard.SocialMediaLink(
                            link.getPlatform(),
                            link.getUrl(),
                            link.getCustomName()))
                    .collect(Collectors.toList()));
        }
        
        card.setSkills(request.getSkills());
        return card;
    }

    public DigitalCardResponse toResponse(DigitalCard card) {
        DigitalCardResponse response = new DigitalCardResponse();
        response.setId(card.getId());
        response.setFullName(card.getFullName());
        response.setProfilePhotoUrl(card.getProfilePhotoUrl());
        response.setTitle(card.getTitle());
        response.setBiography(card.getBiography());
        response.setUsername(card.getUser().getUsername());
        
        if (card.getSocialMediaLinks() != null) {
            response.setSocialMediaLinks(card.getSocialMediaLinks().stream()
                    .map(link -> {
                        DigitalCardResponse.SocialMediaLinkResponse linkResponse = new DigitalCardResponse.SocialMediaLinkResponse();
                        linkResponse.setPlatform(link.getPlatform());
                        linkResponse.setUrl(link.getUrl());
                        linkResponse.setCustomName(link.getCustomName());
                        return linkResponse;
                    })
                    .collect(Collectors.toList()));
        }
        
        response.setSkills(card.getSkills());
        return response;
    }
} 