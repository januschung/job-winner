package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.JobApplication;
import com.tnite.jobwinner.model.Offer;
import com.tnite.jobwinner.model.OfferInput;
import com.tnite.jobwinner.repo.JobApplicationRepository;
import com.tnite.jobwinner.repo.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

	@Mock
	private OfferRepository offerRepository;

	@Mock
	private JobApplicationRepository jobApplicationRepository;

	@InjectMocks
	private OfferService offerService;

	private Offer offer1;
	private Offer offer2;
	private OfferInput offerInput;
	private Offer updatedoffer;
	private JobApplication jobApplication1;
	private JobApplication jobApplication2;

	@BeforeEach
	void setUp() {
		jobApplication1 = new JobApplication(1, "Company A", "QA", "", "", LocalDate.now(), "", "open");
		jobApplication2 = new JobApplication(2, "Company A", "QA", "", "", LocalDate.now(), "", "open");
		offer1 = new Offer(1, 1, LocalDate.now(), "1", "whatever1", jobApplication1);
		offer2 = new Offer(2, 2, LocalDate.now(), "2", "whatever2", jobApplication2);
		offerInput = new OfferInput(1, LocalDate.now(), "1", "whatever1");
		updatedoffer = new Offer(1, 1, LocalDate.now(), "10", "whatsoever1", jobApplication1);
	}

	@Test
	void testAddOffer() {
		when(offerRepository.save(any(Offer.class))).thenReturn(Mono.just(offer1));

		Mono<Offer> result = offerService.addOffer(offerInput);

		StepVerifier.create(result)
			.expectNextMatches(savedoffer -> savedoffer.getJobApplicationId().equals(offerInput.getJobApplicationId()))
			.verifyComplete();

		verify(offerRepository, times(1)).save(any(Offer.class));
	}

	@Test
	void testUpdateOffer() {
		when(offerRepository.findById(1)).thenReturn(Mono.just(offer1));
		when(offerRepository.save(any(Offer.class))).thenReturn(Mono.just(updatedoffer));

		Mono<Offer> result = offerService.updateOffer(updatedoffer);

		StepVerifier.create(result)
			.expectNextMatches(offer -> offer.getDescription().equals("whatsoever1"))
			.verifyComplete();

		verify(offerRepository, times(1)).findById(1);
		verify(offerRepository, times(1)).save(any(Offer.class));
	}

	@Test
	void testDeleteOfferWhenOfferExistsThenReturnsOffer() {
		when(offerRepository.findById(1)).thenReturn(Mono.just(offer1));
		when(offerRepository.delete(offer1)).thenReturn(Mono.empty());

		Mono<Offer> result = offerService.deleteOffer(1);

		StepVerifier.create(result)
			.expectNextMatches(offer -> offer.getDescription().equals("whatever1"))
			.verifyComplete();

		verify(offerRepository, times(1)).findById(1);
		verify(offerRepository, times(1)).delete(offer1);
	}

	@Test
	void testDeleteOfferWhenOfferNotExists() {
		when(offerRepository.findById(1)).thenReturn(Mono.empty());

		Mono<Offer> result = offerService.deleteOffer(1);

		StepVerifier.create(result)
			.verifyComplete();

		verify(offerRepository, times(1)).findById(1);
		verifyNoMoreInteractions(offerRepository);
	}

	@Test
	void getOfferByJobApplicationId() {
		when(offerRepository.findByJobApplicationId(1)).thenReturn(Mono.just(offer1));

		Mono<Offer> result = offerService.offerByJobApplicationId(1);

		StepVerifier.create(result)
			.expectNext(offer1)
			.verifyComplete();

		verify(offerRepository, times(1)).findByJobApplicationId(1);
	}

	@Test
	void testAllOffer() {
		when(offerRepository.findAll()).thenReturn(Flux.just(offer1, offer2));
		when(jobApplicationRepository.findById(1)).thenReturn(Mono.just(jobApplication1));
		when(jobApplicationRepository.findById(2)).thenReturn(Mono.just(jobApplication2));

		Flux<Offer> result = offerService.allOffer();

		StepVerifier.create(result)
			.expectNext(offer1)
			.expectNext(offer2)
			.verifyComplete();

		verify(offerRepository, times(1)).findAll();
		verify(jobApplicationRepository, times(2)).findById(anyInt());
	}
}
