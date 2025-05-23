package com.akgulberk.fullstackdevelopercasespringboot.repository;

import com.akgulberk.fullstackdevelopercasespringboot.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userId);
} 