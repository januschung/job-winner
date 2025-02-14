package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Question;
import com.tnite.jobwinner.model.QuestionInput;
import com.tnite.jobwinner.repo.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class QuestionServiceTest {

	@Mock
	private QuestionRepository questionRepository;

	@InjectMocks
	private QuestionService questionService;

	private Question question1;
	private Question question2;
	private QuestionInput addQuestionInput1;
	private List<Question> questionList;

	@BeforeEach
	void setUp() {
		question1 = new Question();
		question1.setId(1);
		question1.setQuestion("What?");
		question1.setAnswer("hmm");

		question2 = new Question();
		question2.setId(2);
		question2.setQuestion("How?");
		question2.setAnswer("...");

		addQuestionInput1 = new QuestionInput();
		addQuestionInput1.setQuestion("What again?");
		addQuestionInput1.setAnswer("hmm hmm");

		questionList = Arrays.asList(question1, question2);
	}

	@Test
	void testMapping() {
		QuestionInput input = new QuestionInput();
		input.setQuestion("What?");
		input.setAnswer("hmm");

		Question result = questionService.mapping.apply(input);

		assertEquals(input.getQuestion(), result.getQuestion());
		assertEquals(input.getAnswer(), result.getAnswer());
	}

	@Test
	void testAddQuestion() {
		when(questionRepository.save(any(Question.class))).thenReturn(Mono.just(question1));

		Mono<Question> result = questionService.addQuestion(addQuestionInput1);

		StepVerifier.create(result)
			.assertNext(savedQuestion -> {
				assertEquals(question1.getId(), savedQuestion.getId());
				assertEquals(question1.getQuestion(), savedQuestion.getQuestion());
				assertEquals(question1.getAnswer(), savedQuestion.getAnswer());
			})
			.verifyComplete();

		verify(questionRepository, times(1)).save(any(Question.class));
	}

	@Test
	void testUpdateQuestion() {
		when(questionRepository.findById(1)).thenReturn(Mono.just(question1));
		when(questionRepository.save(any(Question.class))).thenReturn(Mono.just(question1));

		Mono<Question> result = questionService.updateQuestion(question1);

		StepVerifier.create(result)
			.assertNext(updatedQuestion -> {
				assertEquals(question1.getId(), updatedQuestion.getId());
				assertEquals(question1.getQuestion(), updatedQuestion.getQuestion());
				assertEquals(question1.getAnswer(), updatedQuestion.getAnswer());
			})
			.verifyComplete();

		verify(questionRepository, times(1)).findById(1);
		verify(questionRepository, times(1)).save(any(Question.class));
	}

	@Test
	void testDeleteQuestionWhenQuestionExistsThenReturnsQuestion() {
		when(questionRepository.findById(1)).thenReturn(Mono.just(question1));
		when(questionRepository.delete(question1)).thenReturn(Mono.empty());

		Mono<Question> result = questionService.deleteQuestion(1);

		StepVerifier.create(result)
			.expectNextMatches(question -> question1.getQuestion().equals("What?"))
			.verifyComplete();

		verify(questionRepository, times(1)).findById(1);
		verify(questionRepository, times(1)).delete(question1);
	}

	@Test
	void testDeleteQuestionWhenQuestionNotExists() {
		when(questionRepository.findById(1)).thenReturn(Mono.empty());

		Mono<Question> result = questionService.deleteQuestion(1);

		StepVerifier.create(result)
			.verifyComplete();

		verify(questionRepository, times(1)).findById(1);
		verifyNoMoreInteractions(questionRepository);
	}

	@Test
	void testAllQuestion() {
		when(questionRepository.findAll()).thenReturn(Flux.fromIterable(questionList));

		Flux<Question> result = questionService.allQuestion();

		StepVerifier.create(result)
			.expectNext(question1)
			.expectNext(question2)
			.verifyComplete();

		verify(questionRepository, times(1)).findAll();
	}

}
