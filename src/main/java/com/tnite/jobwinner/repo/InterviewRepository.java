package com.tnite.jobwinner.repo;

import com.tnite.jobwinner.model.Interview;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InterviewRepository extends ReactiveCrudRepository<Interview, Integer> {
	Flux<Interview> findAllByJobApplicationId(Integer jobApplicationId);

	@Modifying
	@Query("UPDATE interview SET status = 'expired' WHERE interview_date < CURRENT_DATE AND status != 'expired'")
	Mono<Integer> updateExpiredInterviews();
}
