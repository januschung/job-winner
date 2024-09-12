package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.model.AddInterviewInput;
import com.tnite.jobwinner.repo.InterviewRepository;
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

	@InjectMocks
	private InterviewService interviewService;

	private Interview mockInterview1;
	private Interview mockInterview2;
	private AddInterviewInput mockInterviewInput;
	private Interview mockUpdatedInterview;

	@BeforeEach
	void setUp() {
		mockInterview1 = new Interview(1, 1, LocalDate.now(), "John Doe", "Technical Interview", "scheduled");
		mockInterview2 = new Interview(2, 1, LocalDate.now(), "Jane Doe", "HR Interview", "closed");
		mockInterviewInput = new AddInterviewInput(1, LocalDate.now(), "John Doe", "Technical Interview", "scheduled");
		mockUpdatedInterview = new Interview(1, 1, LocalDate.now(), "John Doe", "Technical Interview", "closed");
	}

	@Test
	void testAddInterview() {
		when(interviewRepository.save(any(Interview.class))).thenReturn(Mono.just(mockInterview1));

		Mono<Interview> result = interviewService.addInterview(mockInterviewInput);

		StepVerifier.create(result)
			.expectNextMatches(savedInterview -> savedInterview.getJobApplicationId().equals(mockInterviewInput.getJobApplicationId()))
			.verifyComplete();

		verify(interviewRepository, times(1)).save(any(Interview.class));
	}

	@Test
	void testUpdateInterview() {
		when(interviewRepository.findById(1)).thenReturn(Mono.just(mockInterview1));
		when(interviewRepository.save(any(Interview.class))).thenReturn(Mono.just(mockUpdatedInterview));

		Mono<Interview> result = interviewService.updateInterview(mockUpdatedInterview);

		StepVerifier.create(result)
			.expectNextMatches(interview -> interview.getStatus().equals("closed"))
			.verifyComplete();

		verify(interviewRepository, times(1)).findById(1);
		verify(interviewRepository, times(1)).save(any(Interview.class));
	}

	@Test
	void getInterviewByJobApplicationId() {
		when(interviewRepository.findAllByJobApplicationId(1)).thenReturn(Flux.just(mockInterview1, mockInterview2));

		Flux<Interview> result = interviewService.getInterviewByJobApplicationId(1);

		StepVerifier.create(result)
			.expectNext(mockInterview1)
			.expectNext(mockInterview2)
			.verifyComplete();

		verify(interviewRepository, times(1)).findAllByJobApplicationId(1);
	}

	@Test
	void testGetInterview() {
		when(interviewRepository.findById(1)).thenReturn(Mono.just(mockInterview1));

		Mono<Interview> result = interviewService.getInterview(1);

		StepVerifier.create(result)
			.expectNextMatches(i -> i.getId().equals(1))
			.verifyComplete();

		verify(interviewRepository, times(1)).findById(1);
	}

	@Test
	void testAllInterview() {
		when(interviewRepository.findAll()).thenReturn(Flux.just(mockInterview1, mockInterview2));

		Flux<Interview> result = interviewService.allInterview();

		StepVerifier.create(result)
			.expectNext(mockInterview1)
			.expectNext(mockInterview2)
			.verifyComplete();

		verify(interviewRepository, times(1)).findAll();
	}
}
