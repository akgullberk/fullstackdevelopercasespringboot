package com.akgulberk.fullstackdevelopercasespringboot.service;

import com.akgulberk.fullstackdevelopercasespringboot.dto.UserProfileDTO;
import com.akgulberk.fullstackdevelopercasespringboot.entity.User;
import com.akgulberk.fullstackdevelopercasespringboot.mapper.UserMapper;
import com.akgulberk.fullstackdevelopercasespringboot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;

    @Transactional(readOnly = true)
    public UserProfileDTO getCurrentUserProfile() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı"));
        return userMapper.toProfileDTO(user);
    }

    @Transactional
    public UserProfileDTO updateProfilePhoto(String username, String photoUrl) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı"));

        // Eğer kullanıcının mevcut bir profil fotoğrafı varsa, onu sil
        if (user.getProfilePhotoUrl() != null) {
            fileStorageService.deleteFile(user.getProfilePhotoUrl());
        }

        user.setProfilePhotoUrl(photoUrl);
        User updatedUser = userRepository.save(user);
        return userMapper.toProfileDTO(updatedUser);
    }

    private String getCurrentUsername() {
        return org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + username));
    }
} 