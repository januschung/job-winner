package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.AddInterviewInput;
import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.service.InterviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@Test
	void testAddInterview() {
		AddInterviewInput addInterviewInput = new AddInterviewInput();
		Interview interview = new Interview();
		when(interviewService.addInterview(any(AddInterviewInput.class))).thenReturn(Mono.just(interview));

		Mono<Interview> result = interviewController.addInterview(addInterviewInput);

		assertEquals(interview, result.block());
	}

	@Test
	void testUpdateInterview() {
		Interview Interview = new Interview();
		when(interviewService.updateInterview(any(Interview.class))).thenReturn(Mono.just(Interview));

		Mono<Interview> result = interviewController.updateInterview(Interview);

		assertEquals(Interview, result.block());
	}

	@Test
	void testAllInterview() {
		Interview interview1 = new Interview();
		Interview interview2 = new Interview();
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
		Interview Interview1 = new Interview();
		when(interviewService.getInterview(anyInt())).thenReturn(Mono.just(Interview1));

		Mono<Interview> result = interviewController.interviewById(1);

		assertEquals(Interview1, result.block());
	}
}
