package com.tnite.jobwinner.repo;

import com.tnite.jobwinner.model.Interview;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface InterviewRepository extends ReactiveCrudRepository<Interview, Integer> {
	Flux<Interview> findAllByJobApplicationId(Integer jobApplicationId);
}
