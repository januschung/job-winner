package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.Question;
import com.tnite.jobwinner.model.QuestionInput;
import com.tnite.jobwinner.service.QuestionService;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@MutationMapping
	public Mono<Question> addQuestion(@Argument QuestionInput questionInput) {
		return questionService.addQuestion(questionInput);
	}

	@MutationMapping
	public Mono<Question> updateQuestion(@Argument Question question) {
		return questionService.updateQuestion(question);
	}

	@MutationMapping
	public Mono<Question> deleteQuestion(@Argument @NonNull Integer id) {
		return questionService.deleteQuestion(id);
	}

	@QueryMapping
	public Flux<Question> allQuestion() {
		return questionService.allQuestion();
	}

	@QueryMapping
	public Mono<Question> questionById(@Argument Integer id) {
		return questionService.getQuestionById(id);
	}

}