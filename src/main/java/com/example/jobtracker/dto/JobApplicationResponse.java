package com.example.jobtracker.dto;

import com.example.jobtracker.model.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class JobApplicationResponse {

    private Long id;
    private String company;
    private String position;
    private ApplicationStatus status;
    private LocalDate dateApplied;
    private String notes;
}