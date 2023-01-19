package com.tnite.jobwinner.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.tnite.jobwinner.model.JobApplication;

import reactor.core.publisher.Flux;

public interface JobApplicationRepository extends ReactiveCrudRepository<JobApplication, Integer>{
    Flux<JobApplication> getJobApplicationByCompanyName(String companyName);

}
