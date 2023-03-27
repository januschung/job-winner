package com.tnite.jobwinner.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.tnite.jobwinner.model.JobApplication;

public interface JobApplicationRepository extends ReactiveCrudRepository<JobApplication, Integer>{

}
