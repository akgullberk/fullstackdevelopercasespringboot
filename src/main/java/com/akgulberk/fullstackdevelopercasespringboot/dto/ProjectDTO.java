package com.akgulberk.fullstackdevelopercasespringboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;

    @NotBlank(message = "Proje ismi boş olamaz")
    @Size(max = 40, message = "Proje ismi 40 karakterden uzun olamaz")
    private String name;

    @Size(max = 100, message = "Proje açıklaması 100 karakterden uzun olamaz")
    private String description;

    private List<String> technologies;
    private String githubLink;
} 