package com.example.jobtracker.dto;

import com.example.jobtracker.model.ApplicationStatus;
import com.example.jobtracker.model.JobApplication;

import java.time.LocalDate;

public class JobApplicationMapper {

    public static JobApplication toEntity(JobApplicationRequest request) {
        JobApplication entity = new JobApplication();
        entity.setCompany(request.getCompany());
        entity.setPosition(request.getPosition());
        entity.setStatus(request.getStatus() != null ? request.getStatus() : ApplicationStatus.APPLIED);
        entity.setDateApplied(request.getDateApplied() != null ? request.getDateApplied() : LocalDate.now());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public static void updateEntity(JobApplication entity, JobApplicationRequest request) {
        entity.setCompany(request.getCompany());
        entity.setPosition(request.getPosition());
        entity.setStatus(request.getStatus() != null ? request.getStatus() : entity.getStatus());
        entity.setDateApplied(request.getDateApplied() != null ? request.getDateApplied() : entity.getDateApplied());
        entity.setNotes(request.getNotes());
    }

    public static JobApplicationResponse toResponse(JobApplication entity) {
        return JobApplicationResponse.builder()
                .id(entity.getId())
                .company(entity.getCompany())
                .position(entity.getPosition())
                .status(entity.getStatus())
                .dateApplied(entity.getDateApplied())
                .notes(entity.getNotes())
                .build();
    }
}