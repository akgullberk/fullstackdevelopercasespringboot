package com.akgulberk.fullstackdevelopercasespringboot.service;

import com.akgulberk.fullstackdevelopercasespringboot.dto.ProjectDTO;
import com.akgulberk.fullstackdevelopercasespringboot.entity.Project;
import com.akgulberk.fullstackdevelopercasespringboot.entity.User;
import com.akgulberk.fullstackdevelopercasespringboot.repository.ProjectRepository;
import com.akgulberk.fullstackdevelopercasespringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setTechnologies(projectDTO.getTechnologies());
        project.setGithubLink(projectDTO.getGithubLink());
        project.setUser(user);

        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getUserProjects(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Project> projects = projectRepository.findByUserId(user.getId());
        return projects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDTO getProjectDetails(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı"));

        return convertToDTO(project);
    }

    @Transactional
    public ProjectDTO updateProject(Long projectId, ProjectDTO projectDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Güvenlik kontrolü: Projenin sahibi olan kullanıcı mı güncelliyor?
        if (!project.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You don't have permission to update this project");
        }

        // Güncelleme işlemleri
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setTechnologies(projectDTO.getTechnologies());
        project.setGithubLink(projectDTO.getGithubLink());

        Project updatedProject = projectRepository.save(project);
        return convertToDTO(updatedProject);
    }

    @Transactional
    public void deleteProject(Long projectId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı"));

        // Güvenlik kontrolü: Projenin sahibi olan kullanıcı mı silmeye çalışıyor?
        if (!project.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("Bu projeyi silmek için yetkiniz yok");
        }

        projectRepository.delete(project);
    }

    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTechnologies(),
                project.getGithubLink()
        );
    }
} 