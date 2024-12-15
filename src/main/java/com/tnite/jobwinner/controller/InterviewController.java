package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.model.InterviewInput;
import com.tnite.jobwinner.model.Offer;
import com.tnite.jobwinner.service.InterviewService;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class InterviewController {

	@Autowired
	private InterviewService interviewService;

	@MutationMapping
	public Mono<Interview> addInterview(@Argument InterviewInput interviewInput) {
		return interviewService.addInterview(interviewInput);
	}

	@MutationMapping
	public Mono<Interview> updateInterview(@Argument Interview interview) {
		return interviewService.updateInterview(interview);
	}

	@MutationMapping
	public Mono<Interview> deleteInterview(@Argument @NonNull Integer id) {
		return interviewService.deleteInterview(id);
	}

	@QueryMapping
	public Flux<Interview> allInterviewByJobApplicationId(@Argument Integer jobApplicationId) {
		return interviewService.getInterviewByJobApplicationId(jobApplicationId);
	}

	@QueryMapping
	public Flux<Interview> allInterview() {
		return interviewService.allInterview();
	}

	@QueryMapping
	public Mono<Interview> interviewById(@Argument Integer id) {
		return interviewService.getInterview(id);
	}

}