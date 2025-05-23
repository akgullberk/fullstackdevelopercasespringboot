package com.akgulberk.fullstackdevelopercasespringboot.service;

import com.akgulberk.fullstackdevelopercasespringboot.dto.DigitalCardRequest;
import com.akgulberk.fullstackdevelopercasespringboot.dto.DigitalCardResponse;
import com.akgulberk.fullstackdevelopercasespringboot.dto.DigitalCardWithProjectsResponse;
import com.akgulberk.fullstackdevelopercasespringboot.dto.ProjectDTO;
import com.akgulberk.fullstackdevelopercasespringboot.entity.DigitalCard;
import com.akgulberk.fullstackdevelopercasespringboot.entity.User;
import com.akgulberk.fullstackdevelopercasespringboot.mapper.DigitalCardMapper;
import com.akgulberk.fullstackdevelopercasespringboot.repository.DigitalCardRepository;
import com.akgulberk.fullstackdevelopercasespringboot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DigitalCardService {

    private final DigitalCardRepository digitalCardRepository;
    private final UserRepository userRepository;
    private final DigitalCardMapper digitalCardMapper;
    private final ProjectService projectService;

    @Transactional
    public DigitalCardResponse createDigitalCard(String username, DigitalCardRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + username));

        if (digitalCardRepository.existsByUser(user)) {
            throw new IllegalStateException("Kullanıcının zaten bir dijital kartı var");
        }

        DigitalCard card = digitalCardMapper.toEntity(request);
        card.setUser(user);

        DigitalCard savedCard = digitalCardRepository.save(card);
        return digitalCardMapper.toResponse(savedCard);
    }

    @Transactional(readOnly = true)
    public DigitalCardResponse getDigitalCard(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + username));

        DigitalCard card = digitalCardRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Dijital kart bulunamadı"));

        return digitalCardMapper.toResponse(card);
    }

    @Transactional(readOnly = true)
    public DigitalCardWithProjectsResponse getDigitalCardWithProjects(String username) {
        // Dijital kartı al
        DigitalCardResponse digitalCard = getDigitalCard(username);
        
        // Kullanıcının projelerini al
        List<ProjectDTO> projects = projectService.getUserProjects(username);
        
        // Birleştirilmiş yanıtı oluştur
        return new DigitalCardWithProjectsResponse(digitalCard, projects);
    }

    @Transactional
    public DigitalCardResponse updateDigitalCard(String username, DigitalCardRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + username));

        DigitalCard existingCard = digitalCardRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Dijital kart bulunamadı"));

        DigitalCard updatedCard = digitalCardMapper.toEntity(request);
        updatedCard.setId(existingCard.getId());
        updatedCard.setUser(user);

        DigitalCard savedCard = digitalCardRepository.save(updatedCard);
        return digitalCardMapper.toResponse(savedCard);
    }

    @Transactional
    public void deleteDigitalCard(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı: " + username));

        DigitalCard card = digitalCardRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Dijital kart bulunamadı"));

        digitalCardRepository.delete(card);
    }

    @Transactional(readOnly = true)
    public List<DigitalCardResponse> getAllDigitalCards() {
        return digitalCardRepository.findAll().stream()
                .map(digitalCardMapper::toResponse)
                .collect(Collectors.toList());
    }
} 