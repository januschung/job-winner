package com.tnite.jobwinner.repo;

import com.tnite.jobwinner.model.Offer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface OfferRepository extends ReactiveCrudRepository<Offer, Integer> {
	Mono<Offer> findByJobApplicationId(Integer jobApplicationId);
}
