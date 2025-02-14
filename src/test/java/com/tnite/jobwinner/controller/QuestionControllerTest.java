package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.Question;
import com.tnite.jobwinner.model.QuestionInput;
import com.tnite.jobwinner.service.QuestionService;
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
class QuestionControllerTest {

	@InjectMocks
	private QuestionController questionController;

	@Mock
	private QuestionService questionService;

	@Test
	void testAddQuestion() {
		QuestionInput addQuestionInput = new QuestionInput();
		Question question = new Question();
		when(questionService.addQuestion(any(QuestionInput.class))).thenReturn(Mono.just(question));

		Mono<Question> result = questionController.addQuestion(addQuestionInput);

		assertEquals(question, result.block());
	}

	@Test
	void testUpdateQuestion() {
		Question question = new Question();
		when(questionService.updateQuestion(any(Question.class))).thenReturn(Mono.just(question));

		Mono<Question> result = questionController.updateQuestion(question);

		assertEquals(question, result.block());
	}

	@Test
	void testDeleteQuestion() {
		Question question = new Question();
		when(questionService.deleteQuestion(anyInt())).thenReturn(Mono.just(question));

		Mono<Question> result = questionController.deleteQuestion(1);

		assertEquals(question, result.block());
	}

	@Test
	void testAllQuestion() {
		Question question1 = new Question();
		Question question2 = new Question();
		when(questionService.allQuestion()).thenReturn(Flux.just(question1, question2));

		Flux<Question> result = questionController.allQuestion();

		assertEquals(2, result.collectList().block().size());
		assertEquals(question1, result.collectList().block().get(0));
		assertEquals(question2, result.collectList().block().get(1));
	}

	@Test
	void testAllQuestionWhenNoQuestionsThenReturnEmptyResult() {
		when(questionService.allQuestion()).thenReturn(Flux.empty());

		Flux<Question> result = questionService.allQuestion();

		assertEquals(0, result.collectList().block().size());
	}

}
