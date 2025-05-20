package com.akgulberk.fullstackdevelopercasespringboot.controller;

import com.akgulberk.fullstackdevelopercasespringboot.dto.UserProfileDTO;
import com.akgulberk.fullstackdevelopercasespringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile() {
        UserProfileDTO profileDTO = userService.getCurrentUserProfile();
        return ResponseEntity.ok(profileDTO);
    }
} 