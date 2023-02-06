package com.tnite.jobwinner.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import com.tnite.jobwinner.model.AddJobApplicationInput;
import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.model.UpdateStatusInput;
import com.tnite.jobwinner.repo.JobApplicationRepository;

import graphql.com.google.common.base.Function;
import graphql.com.google.common.base.Objects;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@Slf4j
public class JobApplicationController {

    @Bean
    private WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/graphql").allowedOrigins("http://localhost:3000");
            }
        };
    }
    
    private final JobApplicationRepository jobApplicationRepository;
    
    public JobApplicationController(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }
    
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
    
    @MutationMapping
    public Mono<JobApplication> addJobApplication(@Argument AddJobApplicationInput addJobApplicationInput) {
        Mono<JobApplication> jobApplication = this.jobApplicationRepository.save(mapping.apply(addJobApplicationInput));
        log.info("Added new job application: {}", addJobApplicationInput);
        return jobApplication;
    }
    
    @MutationMapping
    public Mono<JobApplication> updateStatus(@Argument UpdateStatusInput updateStatusInput) {
        log.info("Updating job application id {} with status {}", updateStatusInput.getId(), updateStatusInput.getStatus());
        return this.jobApplicationRepository.findById(updateStatusInput.getId())
                .flatMap(jobApplication -> {
                    jobApplication.setStatus(updateStatusInput.getStatus());
                    return this.jobApplicationRepository.save(jobApplication);
                });
    }
    
    @MutationMapping
    public Mono<JobApplication> deleteJobApplication(@Argument @NonNull Integer id) {
        final Mono<JobApplication> jobApplication = this.jobApplicationRepository.findById(id);
        if (java.util.Objects.isNull(jobApplication)) {
            return Mono.empty();
        }
        log.info("Deleting job application idd {}", id);
        return this.jobApplicationRepository.findById(id).switchIfEmpty(Mono.empty()).filter(java.util.Objects::nonNull)
                .flatMap(jobApplicationToBeDeleted -> this.jobApplicationRepository
                        .delete(jobApplicationToBeDeleted)
                        .then(Mono.just(jobApplicationToBeDeleted)));

    }
    
    @QueryMapping
    public Flux<JobApplication> jobApplicationByCompanyName(@Argument String companyName) {
        return this.jobApplicationByCompanyName(companyName);
    }
    
    @QueryMapping
    public Flux<JobApplication> allJobApplication() {
        return this.jobApplicationRepository.findAll();
    }
    

}