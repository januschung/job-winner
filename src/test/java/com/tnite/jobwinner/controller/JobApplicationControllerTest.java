package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.JobApplicationInput;
import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobApplicationControllerTest {

	@InjectMocks
	private JobApplicationController jobApplicationController;

	@Mock
	private JobApplicationService jobApplicationService;

	@Test
	void testAddJobApplication() {
		JobApplicationInput addJobApplicationInput = new JobApplicationInput();
		JobApplication jobApplication = new JobApplication();
		when(jobApplicationService.addJobApplication(any(JobApplicationInput.class))).thenReturn(Mono.just(jobApplication));

		Mono<JobApplication> result = jobApplicationController.addJobApplication(addJobApplicationInput);

		assertEquals(jobApplication, result.block());
	}

	@Test
	void testUpdateJobApplication() {
		JobApplication jobApplication = new JobApplication();
		when(jobApplicationService.updateJobApplication(any(JobApplication.class))).thenReturn(Mono.just(jobApplication));

		Mono<JobApplication> result = jobApplicationController.updateJobApplication(jobApplication);

		assertEquals(jobApplication, result.block());
	}

	@Test
	void testDeleteJobApplication() {
		JobApplication jobApplication = new JobApplication();
		when(jobApplicationService.deleteJobApplication(anyInt())).thenReturn(Mono.just(jobApplication));

		Mono<JobApplication> result = jobApplicationController.deleteJobApplication(1);

		assertEquals(jobApplication, result.block());
	}

	@Test
	void testAllJobApplication() {
		JobApplication jobApplication1 = new JobApplication();
		JobApplication jobApplication2 = new JobApplication();
		when(jobApplicationService.allJobApplication()).thenReturn(Flux.just(jobApplication1, jobApplication2));

		Flux<JobApplication> result = jobApplicationController.allJobApplication();

		assertEquals(2, result.collectList().block().size());
		assertEquals(jobApplication1, result.collectList().block().get(0));
		assertEquals(jobApplication2, result.collectList().block().get(1));
	}

	@Test
	void testAllJobApplicationWhenNoJobApplicationsThenReturnEmptyResult() {
		when(jobApplicationService.allJobApplication()).thenReturn(Flux.empty());

		Flux<JobApplication> result = jobApplicationService.allJobApplication();

		assertEquals(0, result.collectList().block().size());
	}

	@Test
	void testSearchJobApplicationByIdReturnOneResult() {
		JobApplication jobApplication = new JobApplication();
		when(jobApplicationService.searchJobApplications(anyString())).thenReturn(Flux.just(jobApplication));

		Flux<JobApplication> result = jobApplicationController.searchJobApplications("foo");

		assertEquals(1, result.collectList().block().size());
	}

	@Test
	void testSearchJobApplicationByIdReturnTwoResults() {
		JobApplication jobApplication1 = new JobApplication();
		JobApplication jobApplication2 = new JobApplication();
		when(jobApplicationService.searchJobApplications(anyString())).thenReturn(Flux.just(jobApplication1, jobApplication2));

		Flux<JobApplication> result = jobApplicationController.searchJobApplications("foo");

		assertEquals(2, result.collectList().block().size());
	}

	@Test
	void testSearchJobApplicationByIdReturnEmptyResults() {
		when(jobApplicationService.searchJobApplications(anyString())).thenReturn(Flux.empty());

		Flux<JobApplication> result = jobApplicationController.searchJobApplications("foo");

		assertEquals(0, result.collectList().block().size());
	}

}
