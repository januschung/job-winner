package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Question;
import com.tnite.jobwinner.model.QuestionInput;
import com.tnite.jobwinner.repo.QuestionRepository;
import graphql.com.google.common.base.Function;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	Function<QuestionInput, Question> mapping = q -> {
		var question = new Question();
		question.setQuestion(q.getQuestion());
		question.setAnswer(q.getAnswer());

		return question;
	};


	public Mono<Question> addQuestion(QuestionInput addQuestionInput) {
		Mono<Question> question = questionRepository.save(mapping.apply(addQuestionInput));
		log.info("Added new question: {}", addQuestionInput);
		return question;
	}

	public Mono<Question> updateQuestion(Question question) {
		log.info("Updating question id {}", question.getId());
		return this.questionRepository.findById(question.getId())
			.flatMap(q -> {
				q.setQuestion(question.getQuestion());
				q.setAnswer(question.getAnswer());
				return questionRepository.save(question).log();
			});
	}

	public Mono<Question> deleteQuestion(@NonNull Integer id) {
		log.info("Deleting question id {}", id);
		return this.questionRepository.findById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
			.flatMap(QuestionToBeDeleted -> questionRepository
				.delete(QuestionToBeDeleted)
				.then(Mono.just(QuestionToBeDeleted))).log();
	}

	public Flux<Question> allQuestion() {
		return this.questionRepository.findAll().log();
	}

	public Mono<Question> getQuestionById(Integer id) {
		return questionRepository.findById(id);
	}

}
