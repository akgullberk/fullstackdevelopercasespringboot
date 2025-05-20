package com.akgulberk.fullstackdevelopercasespringboot.service;

import com.akgulberk.fullstackdevelopercasespringboot.dto.UserProfileDTO;
import com.akgulberk.fullstackdevelopercasespringboot.entity.User;
import com.akgulberk.fullstackdevelopercasespringboot.mapper.UserMapper;
import com.akgulberk.fullstackdevelopercasespringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserProfileDTO getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toProfileDTO(user);
    }
} 