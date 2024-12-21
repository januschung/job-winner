package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.model.InterviewInput;
import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.repo.InterviewRepository;
import com.tnite.jobwinner.repo.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterviewServiceTest {

	@Mock
	private InterviewRepository interviewRepository;

	@Mock
	private JobApplicationRepository jobApplicationRepository;

	@InjectMocks
	private InterviewService interviewService;

	private Interview interview1;
	private Interview interview2;
	private InterviewInput interviewInput;
	private Interview updatedInterview;
	private JobApplication jobApplication;

	@BeforeEach
	void setUp() {
		jobApplication = new JobApplication(1, "Company A", "QA", "", "", LocalDate.now(), "", "abc");
		interview1 = new Interview(1, 1, LocalDate.now(), "John Doe", "Technical Interview", "scheduled", jobApplication);
		interview2 = new Interview(2, 1, LocalDate.now(), "Jane Doe", "HR Interview", "closed", jobApplication);
		interviewInput = new InterviewInput(1, LocalDate.now(), "John Doe", "Technical Interview", "scheduled");
		updatedInterview = new Interview(1, 1, LocalDate.now(), "John Doe", "Technical Interview", "closed", jobApplication);
	}

	@Test
	void testAddInterview() {
		when(interviewRepository.save(any(Interview.class))).thenReturn(Mono.just(interview1));

		Mono<Interview> result = interviewService.addInterview(interviewInput);

		StepVerifier.create(result)
			.expectNextMatches(savedInterview -> savedInterview.getJobApplicationId().equals(interviewInput.getJobApplicationId()))
			.verifyComplete();

		verify(interviewRepository, times(1)).save(any(Interview.class));
	}

	@Test
	void testUpdateInterview() {
		when(interviewRepository.findById(1)).thenReturn(Mono.just(interview1));
		when(interviewRepository.save(any(Interview.class))).thenReturn(Mono.just(updatedInterview));

		Mono<Interview> result = interviewService.updateInterview(updatedInterview);

		StepVerifier.create(result)
			.expectNextMatches(interview -> interview.getStatus().equals("closed"))
			.verifyComplete();

		verify(interviewRepository, times(1)).findById(1);
		verify(interviewRepository, times(1)).save(any(Interview.class));
	}

	@Test
	void testDeleteInterviewWhenInterviewExistsThenReturnsInterview() {
		when(interviewRepository.findById(1)).thenReturn(Mono.just(interview1));
		when(interviewRepository.delete(interview1)).thenReturn(Mono.empty());

		Mono<Interview> result = interviewService.deleteInterview(1);

		StepVerifier.create(result)
			.expectNextMatches(interview -> interview.getDescription().equals("Technical Interview"))
			.verifyComplete();

		verify(interviewRepository, times(1)).findById(1);
		verify(interviewRepository, times(1)).delete(interview1);
	}

	@Test
	void testDeleteInterviewWhenInterviewNotExists() {
		when(interviewRepository.findById(1)).thenReturn(Mono.empty());

		Mono<Interview> result = interviewService.deleteInterview(1);

		StepVerifier.create(result)
			.verifyComplete();

		verify(interviewRepository, times(1)).findById(1);
		verifyNoMoreInteractions(interviewRepository);
	}

	@Test
	void getInterviewByJobApplicationId() {
		when(interviewRepository.findAllByJobApplicationId(1)).thenReturn(Flux.just(interview1, interview2));

		Flux<Interview> result = interviewService.getInterviewByJobApplicationId(1);

		StepVerifier.create(result)
			.expectNext(interview1)
			.expectNext(interview2)
			.verifyComplete();

		verify(interviewRepository, times(1)).findAllByJobApplicationId(1);
	}

	@Test
	void testGetInterview() {
		when(interviewRepository.findById(1)).thenReturn(Mono.just(interview1));
		when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication));

		Mono<Interview> result = interviewService.getInterviewById(1);

		StepVerifier.create(result)
			.expectNextMatches(i -> i.getId().equals(1))
			.verifyComplete();

		verify(interviewRepository, times(1)).findById(1);
	}

	@Test
	void testAllInterview() {
		when(interviewRepository.findAll()).thenReturn(Flux.just(interview1, interview2));
		when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication));

		Flux<Interview> result = interviewService.allInterview();

		StepVerifier.create(result)
			.expectNext(interview1)
			.expectNext(interview2)
			.verifyComplete();

		verify(interviewRepository, times(1)).findAll();
		verify(jobApplicationRepository, times(2)).findById(anyInt());
	}
}
