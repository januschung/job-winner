package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.OfferInput;
import com.tnite.jobwinner.model.Offer;
import com.tnite.jobwinner.service.OfferService;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class OfferController {

	@Autowired
	private OfferService offerService;

	@MutationMapping
	public Mono<Offer> addOffer(@Argument OfferInput offerInput) {
		return offerService.addOffer(offerInput);
	}

	@MutationMapping
	public Mono<Offer> updateOffer(@Argument Offer offer) {
		return offerService.updateOffer(offer);
	}

	@MutationMapping
	public Mono<Offer> deleteOffer(@Argument @NonNull Integer id) {
		return offerService.deleteOffer(id);
	}

	@QueryMapping
	public Flux<Offer> allOffer() {
		return offerService.allOffer();
	}

	@QueryMapping
	public Mono<Offer> offerByJobApplicationId(@Argument Integer jobApplicationId) {
		return offerService.offerByJobApplicationId(jobApplicationId);
	}

}