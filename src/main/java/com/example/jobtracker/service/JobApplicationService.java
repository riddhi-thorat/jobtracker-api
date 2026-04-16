package com.example.jobtracker.service;

import com.example.jobtracker.dto.JobApplicationMapper;
import com.example.jobtracker.dto.JobApplicationRequest;
import com.example.jobtracker.dto.JobApplicationResponse;
import com.example.jobtracker.model.ApplicationStatus;
import com.example.jobtracker.model.JobApplication;
import com.example.jobtracker.model.User;
import com.example.jobtracker.repository.JobApplicationRepository;
import com.example.jobtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository repository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<JobApplicationResponse> getAllApplications() {
        User user = getCurrentUser();
        return repository.findByUserId(user.getId())
                .stream()
                .map(JobApplicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public JobApplicationResponse getApplicationById(Long id) {
        User user = getCurrentUser();
        JobApplication entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        if (!entity.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return JobApplicationMapper.toResponse(entity);
    }

    public JobApplicationResponse createApplication(JobApplicationRequest request) {
        User user = getCurrentUser();
        JobApplication entity = JobApplicationMapper.toEntity(request);
        entity.setUser(user);
        JobApplication saved = repository.save(entity);
        return JobApplicationMapper.toResponse(saved);
    }

    public JobApplicationResponse updateApplication(Long id, JobApplicationRequest request) {
        User user = getCurrentUser();
        JobApplication existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        JobApplicationMapper.updateEntity(existing, request);
        JobApplication saved = repository.save(existing);
        return JobApplicationMapper.toResponse(saved);
    }

    public void deleteApplication(Long id) {
        User user = getCurrentUser();
        JobApplication existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        repository.deleteById(id);
    }

    public List<JobApplicationResponse> getByStatus(ApplicationStatus status) {
        User user = getCurrentUser();
        return repository.findByUserIdAndStatus(user.getId(), status)
                .stream()
                .map(JobApplicationMapper::toResponse)
                .collect(Collectors.toList());
    }
}