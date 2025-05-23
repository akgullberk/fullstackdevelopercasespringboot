package com.akgulberk.fullstackdevelopercasespringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitalCardWithProjectsResponse {
    private DigitalCardResponse digitalCard;
    private List<ProjectDTO> projects;
} 