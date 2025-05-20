package com.akgulberk.fullstackdevelopercasespringboot.repository;

import com.akgulberk.fullstackdevelopercasespringboot.entity.DigitalCard;
import com.akgulberk.fullstackdevelopercasespringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DigitalCardRepository extends JpaRepository<DigitalCard, Long> {
    Optional<DigitalCard> findByUser(User user);
    boolean existsByUser(User user);
} 