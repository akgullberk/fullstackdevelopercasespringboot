package com.akgulberk.fullstackdevelopercasespringboot.mapper;

import com.akgulberk.fullstackdevelopercasespringboot.dto.UserProfileDTO;
import com.akgulberk.fullstackdevelopercasespringboot.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public UserProfileDTO toProfileDTO(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserProfileDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getName(),
            user.getSurname()
        );
    }
} 