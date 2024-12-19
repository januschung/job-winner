package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Interview;
import com.tnite.jobwinner.model.OfferInput;
import com.tnite.jobwinner.model.Offer;
import com.tnite.jobwinner.repo.JobApplicationRepository;
import com.tnite.jobwinner.repo.OfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j
public class OfferService {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private JobApplicationRepository jobApplicationRepository;

	private Offer mapToOffer(OfferInput offerInput) {
		var offer = new Offer();
		offer.setJobApplicationId(offerInput.getJobApplicationId());
		offer.setOfferDate(offerInput.getOfferDate());
		offer.setSalaryOffered(offerInput.getSalaryOffered());
		offer.setDescription(offerInput.getDescription());
		return offer;
	}

	public Mono<Offer> addOffer(OfferInput offerInput) {
		Offer offer = mapToOffer(offerInput);
		return offerRepository.save(offer)
			.doOnSuccess(p -> log.info("Added new Offer: {}", p))
			.doOnError(e -> log.error("Failed to add Offer: {}", offerInput, e));
	}

	public Mono<Offer> updateOffer(Offer offer) {
		return offerRepository.findById(offer.getId())
			.flatMap(existingOffer -> {
				updateOfferDetails(existingOffer, offer);
				return offerRepository.save(existingOffer);
			})
			.doOnSuccess(p -> log.info("Updated Offer: {}", p))
			.doOnError(e -> log.error("Failed to update Offer: {}", offer, e));
	}

	private void updateOfferDetails(Offer existingOffer, Offer updatedOffer) {
		existingOffer.setJobApplicationId(updatedOffer.getJobApplicationId());
		existingOffer.setOfferDate(updatedOffer.getOfferDate());
		existingOffer.setSalaryOffered(updatedOffer.getSalaryOffered());
		existingOffer.setDescription(updatedOffer.getDescription());
	}

	public Mono<Offer> deleteOffer(Integer id) {
		log.info("Deleting offer id {}", id);
		return this.offerRepository.findById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
			.flatMap(offerToBeDeleted -> offerRepository
				.delete(offerToBeDeleted)
				.then(Mono.just(offerToBeDeleted))).log();
	}

	public Flux<Offer> allOffer() {
		return offerRepository.findAll()
			.flatMap(offer ->
				jobApplicationRepository.findById(offer.getJobApplicationId())
					.map(jobApplication -> {
						offer.setJobApplication(jobApplication);
						return offer;
					})
					.defaultIfEmpty(offer)  // If JobApplication not found, just return the offer without setting JobApplication
					.doOnSuccess(o -> log.info("Retrieved offer with job application: {}", o))
					.doOnError(e -> log.error("Failed to fetch job application for offer", e))
			)
			.doOnComplete(() -> log.info("Retrieved all offers"))
			.doOnError(e -> log.error("Failed to retrieve offers", e));
	}

	public Mono<Offer> offerByJobApplicationId(Integer jobApplicationId) {
		return offerRepository.findByJobApplicationId(jobApplicationId)
			.switchIfEmpty(Mono.defer(() ->{
				log.warn("Offer with jobApplicationId {} not found", jobApplicationId);
				return Mono.empty();
			}))
			.doOnSuccess(offer -> log.info("Retrieved Offer: {}", offer))
			.doOnError(e -> log.error("Failed to retrieve Offer with jobApplicationId {}", jobApplicationId, e));
	}

}
