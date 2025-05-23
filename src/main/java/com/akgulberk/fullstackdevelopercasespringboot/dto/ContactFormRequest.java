package com.akgulberk.fullstackdevelopercasespringboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactFormRequest {
    @NotBlank(message = "İsim alanı boş bırakılamaz")
    @Size(max = 100, message = "İsim 100 karakterden uzun olamaz")
    private String name;

    @NotBlank(message = "E-posta alanı boş bırakılamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    private String email;

    @NotBlank(message = "Konu alanı boş bırakılamaz")
    @Size(max = 200, message = "Konu 200 karakterden uzun olamaz")
    private String subject;

    @NotBlank(message = "Mesaj alanı boş bırakılamaz")
    @Size(max = 1000, message = "Mesaj 1000 karakterden uzun olamaz")
    private String message;

    @NotBlank(message = "Alıcı kullanıcı adı boş bırakılamaz")
    private String recipientUsername;
} 