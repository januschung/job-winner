package com.tnite.jobwinner.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnite.jobwinner.model.AddJobApplicationInput;
import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.repo.JobApplicationRepository;

import graphql.com.google.common.base.Function;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class JobApplicationService {
    
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    
    Function<AddJobApplicationInput, JobApplication> mapping = aji -> {
        var jobApplication = new JobApplication();
        jobApplication.setCompanyName(aji.getCompanyName());
        jobApplication.setJobTitle(aji.getJobTitle());
        jobApplication.setSalaryRange(aji.getSalaryRange());
        jobApplication.setJobUrl(aji.getJobUrl());
        jobApplication.setAppliedDate(aji.getAppliedDate());
        jobApplication.setDescription(aji.getDescription());
        jobApplication.setStatus(aji.getStatus());
        return jobApplication;
    };
    
    Function<JobApplication, JobApplication> editMapping = ji -> {
        var jobApplication = new JobApplication();
        jobApplication.setId(ji.getId());
        jobApplication.setCompanyName(ji.getCompanyName());
        jobApplication.setJobTitle(ji.getJobTitle());
        jobApplication.setSalaryRange(ji.getSalaryRange());
        jobApplication.setJobUrl(ji.getJobUrl());
        jobApplication.setAppliedDate(ji.getAppliedDate());
        jobApplication.setDescription(ji.getDescription());
        jobApplication.setStatus(ji.getStatus());
        return jobApplication;
    };
    
    
    public Mono<JobApplication> addJobApplication(AddJobApplicationInput addJobApplicationInput) {
        Mono<JobApplication> jobApplication = jobApplicationRepository.save(mapping.apply(addJobApplicationInput));
        log.info("Added new job application: {}", addJobApplicationInput);
        return jobApplication;
    }
    

    public Mono<JobApplication> updateJobApplication(JobApplication jobApplication) {
        log.info("Updating job application id {}", jobApplication.getId());
        return this.jobApplicationRepository.findById(jobApplication.getId())
                .flatMap(j -> {
                    j.setCompanyName(jobApplication.getCompanyName());
                    j.setJobTitle(jobApplication.getJobTitle());
                    j.setSalaryRange(jobApplication.getSalaryRange());
                    j.setJobUrl(jobApplication.getJobUrl());
                    j.setAppliedDate(jobApplication.getAppliedDate());
                    j.setDescription(jobApplication.getDescription());
                    j.setStatus(jobApplication.getStatus());
                    return jobApplicationRepository.save(jobApplication).log();
                });
    }
    

    public Mono<JobApplication> deleteJobApplication(@NonNull Integer id) {
        final Mono<JobApplication> jobApplication = jobApplicationRepository.findById(id);
        if (Objects.isNull(jobApplication)) {
            return Mono.empty();
        }
        log.info("Deleting job application idd {}", id);
        return this.jobApplicationRepository.findById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
                .flatMap(jobApplicationToBeDeleted -> jobApplicationRepository
                        .delete(jobApplicationToBeDeleted)
                        .then(Mono.just(jobApplicationToBeDeleted))).log();

    }
    

    public Flux<JobApplication> allJobApplication() {
        return this.jobApplicationRepository.findAll().log();
    }


    public Flux<JobApplication> searchJobApplications(String searchTerm) {
        return jobApplicationRepository.searchJobApplications(searchTerm);
    }
}
