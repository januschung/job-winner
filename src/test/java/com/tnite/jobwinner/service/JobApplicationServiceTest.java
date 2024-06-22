package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.AddJobApplicationInput;
import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.repo.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobApplicationServiceTest {

	@Mock
	private JobApplicationRepository jobApplicationRepository;

	@InjectMocks
	private JobApplicationService jobApplicationService;

	private JobApplication jobApplication1;
	private JobApplication jobApplication2;
	private AddJobApplicationInput addJobApplicationInput1;
	private AddJobApplicationInput addJobApplicationInput2;
	private List<JobApplication> jobApplicationList;

	@BeforeEach
	void setUp() {
		jobApplication1 = new JobApplication();
		jobApplication1.setId(1);
		jobApplication1.setCompanyName("Company A");
		jobApplication1.setJobTitle("Developer");

		jobApplication2 = new JobApplication();
		jobApplication2.setId(2);
		jobApplication2.setCompanyName("Company B");
		jobApplication2.setJobTitle("Manager");

		addJobApplicationInput1 = new AddJobApplicationInput();
		addJobApplicationInput1.setCompanyName("Company A");
		addJobApplicationInput1.setJobTitle("Developer");

		addJobApplicationInput2 = new AddJobApplicationInput();
		addJobApplicationInput2.setCompanyName("Company B");
		addJobApplicationInput2.setJobTitle("Manager");

		jobApplicationList = Arrays.asList(jobApplication1, jobApplication2);
	}

	@Test
	void testAddJobApplication() {
		when(jobApplicationRepository.save(ArgumentMatchers.any(JobApplication.class))).thenReturn(Mono.just(jobApplication1));

		Mono<JobApplication> result = jobApplicationService.addJobApplication(addJobApplicationInput1);

		StepVerifier.create(result)
			.assertNext(savedJobApplication -> {
				assertEquals(jobApplication1.getId(), savedJobApplication.getId());
				assertEquals(jobApplication1.getCompanyName(), savedJobApplication.getCompanyName());
				assertEquals(jobApplication1.getJobTitle(), savedJobApplication.getJobTitle());
			})
			.verifyComplete();

		verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
	}

	@Test
	void testUpdateJobApplication() {
		when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
		when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(Mono.just(jobApplication1));

		Mono<JobApplication> result = jobApplicationService.updateJobApplication(jobApplication1);

		StepVerifier.create(result)
			.assertNext(updatedJobApplication -> {
				assertEquals(jobApplication1.getId(), updatedJobApplication.getId());
				assertEquals(jobApplication1.getCompanyName(), updatedJobApplication.getCompanyName());
				assertEquals(jobApplication1.getJobTitle(), updatedJobApplication.getJobTitle());
			})
			.verifyComplete();

		verify(jobApplicationRepository, times(1)).findById(1);
		verify(jobApplicationRepository, times(1)).save(any(JobApplication.class));
	}

	@Test
	void testAllJobApplication() {
		when(jobApplicationRepository.findAll()).thenReturn(Flux.fromIterable(jobApplicationList));

		Flux<JobApplication> result = jobApplicationService.allJobApplication();

		StepVerifier.create(result)
			.expectNext(jobApplication1)
			.expectNext(jobApplication2)
			.verifyComplete();

		verify(jobApplicationRepository, times(1)).findAll();
	}

	@Test
	void testSearchJobApplications() {
		when(jobApplicationRepository.searchJobApplications(anyString()))
			.thenReturn(Flux.fromIterable(jobApplicationList));

		Flux<JobApplication> result = jobApplicationService.searchJobApplications("whatever");

		StepVerifier.create(result)
			.expectNext(jobApplication1)
			.expectNext(jobApplication2)
			.verifyComplete();

		verify(jobApplicationRepository, times(1))
			.searchJobApplications("whatever");
	}
}
