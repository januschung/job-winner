package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.model.Offer;
import com.tnite.jobwinner.model.OfferInput;
import com.tnite.jobwinner.service.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferControllerTest {

	@InjectMocks
	private OfferController offerController;

	@Mock
	private OfferService offerService;

	@Test
	void testAddOffer() {
		OfferInput offerInput = new OfferInput();
		Offer Offer = new Offer();
		when(offerService.addOffer(any(OfferInput.class))).thenReturn(Mono.just(Offer));

		Mono<Offer> result = offerController.addOffer(offerInput);

		assertEquals(Offer, result.block());
	}

	@Test
	void testUpdateOffer() {
		Offer offer = new Offer();
		when(offerService.updateOffer(any(Offer.class))).thenReturn(Mono.just(offer));

		Mono<Offer> result = offerController.updateOffer(offer);

		assertEquals(offer, result.block());
	}

	@Test
	void testDeleteOffer() {
		Offer offer = new Offer();
		when(offerService.deleteOffer(anyInt())).thenReturn(Mono.just(offer));

		Mono<Offer> result = offerController.deleteOffer(1);

		assertEquals(offer, result.block());
	}

	@Test
	void testAllOffer() {
		Offer offer1 = new Offer();
		Offer offer2 = new Offer();
		when(offerService.allOffer()).thenReturn(Flux.just(offer1, offer2));

		Flux<Offer> result = offerController.allOffer();

		assertEquals(2, result.collectList().block().size());
		assertEquals(offer1, result.collectList().block().get(0));
		assertEquals(offer2, result.collectList().block().get(1));
	}

	@Test
	void testAllOfferWhenNoOffersThenReturnEmptyResult() {
		when(offerService.allOffer()).thenReturn(Flux.empty());

		Flux<Offer> result = offerController.allOffer();

		assertEquals(0, result.collectList().block().size());
	}

	@Test
	void testOfferByJobApplicationId() {
		Offer offer1 = new Offer();
		when(offerService.offerByJobApplicationId(anyInt())).thenReturn(Mono.just(offer1));

		Mono<Offer> result = offerController.offerByJobApplicationId(1);

		assertEquals(offer1, result.block());
	}

	@Test
	void testOfferByJobApplicationIdWhenNoOffersThenReturnEmptyResult() {
		when(offerService.offerByJobApplicationId(anyInt())).thenReturn(Mono.empty());

		Mono<Offer> result = offerController.offerByJobApplicationId(1);

		assertTrue(result.blockOptional().isEmpty());
	}

}
