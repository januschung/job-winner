package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.InterviewInput;
import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.service.InterviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterviewControllerTest {

	@InjectMocks
	private InterviewController interviewController;

	@Mock
	private InterviewService interviewService;

	private Interview interview1;
	private Interview interview2;
	private JobApplication jobApplication;

	@BeforeEach
	void setUp(){
		jobApplication = new JobApplication(1, "Company A", "QA", "", "", LocalDate.now(), "", "", "abc");
		interview1 = new Interview(1, 1, LocalDate.now(), "John Doe", "HR Interview", "scheduled", jobApplication);
		interview2 = new Interview(2, 1, LocalDate.now(), "Jane Doe", "Technical Interview", "scheduled", jobApplication);
	}

	@Test
	void testAddInterview() {
		InterviewInput addInterviewInput = new InterviewInput();
		when(interviewService.addInterview(any(InterviewInput.class))).thenReturn(Mono.just(interview1));

		Mono<Interview> result = interviewController.addInterview(addInterviewInput);

		assertEquals(interview1, result.block());
	}

	@Test
	void testUpdateInterview() {
		when(interviewService.updateInterview(any(Interview.class))).thenReturn(Mono.just(interview1));

		Mono<Interview> result = interviewController.updateInterview(interview1);

		assertEquals(interview1, result.block());
	}

	@Test
	void testDeleteInterview() {
		when(interviewService.deleteInterview(anyInt())).thenReturn(Mono.just(interview1));

		Mono<Interview> result = interviewController.deleteInterview(1);

		assertEquals(interview1, result.block());
	}

	@Test
	void testAllInterviewByJobApplicationId() {
		when(interviewService.getInterviewByJobApplicationId(1)).thenReturn(Flux.just(interview1, interview2));

		Flux<Interview> result = interviewController.allInterviewByJobApplicationId(1);

		assertEquals(2, result.collectList().block().size());
		assertEquals(interview1, result.collectList().block().get(0));
		assertEquals(interview2, result.collectList().block().get(1));
	}

	@Test
	void testAllInterview() {
		when(interviewService.allInterview()).thenReturn(Flux.just(interview1, interview2));

		Flux<Interview> result = interviewController.allInterview();

		assertEquals(2, result.collectList().block().size());
		assertEquals(interview1, result.collectList().block().get(0));
		assertEquals(interview2, result.collectList().block().get(1));
	}

	@Test
	void testAllInterviewWhenNoInterviewsThenReturnEmptyResult() {
		when(interviewService.allInterview()).thenReturn(Flux.empty());

		Flux<Interview> result = interviewController.allInterview();

		assertEquals(0, result.collectList().block().size());
	}

	@Test
	void testGetInterview() {
		when(interviewService.getInterviewById(anyInt())).thenReturn(Mono.just(interview1));

		Mono<Interview> result = interviewController.interviewById(1);

		assertEquals(interview1, result.block());
	}
}
