package com.example.jobtracker.dto;

import com.example.jobtracker.model.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobApplicationRequest {

    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name must be under 100 characters")
    private String company;

    @NotBlank(message = "Position is required")
    @Size(max = 100, message = "Position must be under 100 characters")
    private String position;

    private ApplicationStatus status;

    private LocalDate dateApplied;

    @Size(max = 1000, message = "Notes must be under 1000 characters")
    private String notes;
}