package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.model.InterviewInput;
import com.tnite.jobwinner.model.Offer;
import com.tnite.jobwinner.repo.InterviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;

	private Interview mapToInterview(InterviewInput interviewInput) {
		var interview = new Interview();
		interview.setJobApplicationId(interviewInput.getJobApplicationId());
		interview.setInterviewDate(interviewInput.getInterviewDate());
		interview.setInterviewer(interviewInput.getInterviewer());
		interview.setDescription(interviewInput.getDescription());
		interview.setStatus(interviewInput.getStatus());
		return interview;
	}

	public Mono<Interview> addInterview(InterviewInput interviewInput) {
		Interview interview = mapToInterview(interviewInput);
		return interviewRepository.save(interview)
			.doOnSuccess(p -> log.info("Added new interview: {}", p))
			.doOnError(e -> log.error("Failed to add interview: {}", interviewInput, e));
	}

	public Mono<Interview> updateInterview(Interview interview) {
		return interviewRepository.findById(interview.getId())
			.flatMap(existingInterview -> {
				updateInterviewDetails(existingInterview, interview);
				return interviewRepository.save(existingInterview);
			})
			.doOnSuccess(p -> log.info("Updated interview: {}", p))
			.doOnError(e -> log.error("Failed to update interview: {}", interview, e));
	}

	public Mono<Interview> deleteInterview(Integer id) {
		log.info("Deleting interview id {}", id);
		return this.interviewRepository.findById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
			.flatMap(interviewToBeDeleted -> interviewRepository
				.delete(interviewToBeDeleted)
				.then(Mono.just(interviewToBeDeleted))).log();
	}

	private void updateInterviewDetails(Interview existingInterview, Interview updatedInterview) {
		existingInterview.setJobApplicationId(updatedInterview.getJobApplicationId());
		existingInterview.setInterviewDate(updatedInterview.getInterviewDate());
		existingInterview.setInterviewer(updatedInterview.getInterviewer());
		existingInterview.setDescription(updatedInterview.getDescription());
		existingInterview.setStatus(updatedInterview.getStatus());
	}

	public Flux<Interview> getInterviewByJobApplicationId(Integer jobApplicationId) {
		return interviewRepository.findAllByJobApplicationId(jobApplicationId)
			.doOnComplete(() -> log.info("Retrieved all interviews for job application id {}", jobApplicationId))
			.doOnError(e -> log.error("Failed to retrieve all interviews for job application id {}", jobApplicationId, e));
	}

	public Flux<Interview> allInterview() {
		return interviewRepository.findAll()
			.doOnComplete(() -> log.info("Retrieved all interviews"))
			.doOnError(e -> log.error("Failed to retrieve interviews", e));
	}

	public Mono<Interview> getInterview(Integer id) {
		return interviewRepository.findById(id)
			.switchIfEmpty(Mono.defer(() -> {
				log.warn("Interview with id {} not found", id);
				return Mono.empty();
			}))
			.doOnSuccess(profile -> log.info("Retrieved interview: {}", profile))
			.doOnError(e -> log.error("Failed to retrieve interview with id {}", id, e));
	}

}
