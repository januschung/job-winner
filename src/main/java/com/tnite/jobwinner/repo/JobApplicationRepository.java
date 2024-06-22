package com.tnite.jobwinner.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.tnite.jobwinner.model.JobApplication;
import reactor.core.publisher.Flux;

public interface JobApplicationRepository extends ReactiveCrudRepository<JobApplication, Integer>{

	@Query("SELECT * FROM job_application " +
		"WHERE LOWER(company_name) LIKE " +
		"LOWER(CONCAT('%', :searchTerm, '%')) " +
		"OR LOWER(job_title) LIKE " +
		"LOWER(CONCAT('%', :searchTerm, '%')) " +
		"OR LOWER(description) LIKE " +
		"LOWER(CONCAT('%', :searchTerm, '%')) "
	)
	Flux<JobApplication> searchJobApplications(String searchTerm);

}
