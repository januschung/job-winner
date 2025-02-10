package com.tnite.jobwinner.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnite.jobwinner.model.FrequentUrlInput;
import com.tnite.jobwinner.model.FrequentUrl;
import com.tnite.jobwinner.repo.FrequentUrlRepository;

import graphql.com.google.common.base.Function;
import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FrequentUrlService {

	@Autowired
	private FrequentUrlRepository frequentUrlRepository;

	Function<FrequentUrlInput, FrequentUrl> mapping = fu -> {
		var frequentUrl = new FrequentUrl();
		frequentUrl.setTitle(fu.getTitle());
		frequentUrl.setUrl(fu.getUrl());

		return frequentUrl;
	};


	public Mono<FrequentUrl> addFrequentUrl(FrequentUrlInput addFrequentUrlInput) {
		Mono<FrequentUrl> frequentUrl = frequentUrlRepository.save(mapping.apply(addFrequentUrlInput));
		log.info("Added new frequent url: {}", addFrequentUrlInput);
		return frequentUrl;
	}

	public Mono<FrequentUrl> updateFrequentUrl(FrequentUrl frequentUrl) {
		log.info("Updating frequent url id {}", frequentUrl.getId());
		return this.frequentUrlRepository.findById(frequentUrl.getId())
			.flatMap(fu -> {
				fu.setTitle(frequentUrl.getTitle());
				fu.setUrl(frequentUrl.getUrl());
				return frequentUrlRepository.save(frequentUrl).log();
			});
	}

	public Mono<FrequentUrl> deleteFrequentUrl(@NonNull Integer id) {
		log.info("Deleting frequent url id {}", id);
		return this.frequentUrlRepository.findById(id).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
			.flatMap(FrequentUrlToBeDeleted -> frequentUrlRepository
				.delete(FrequentUrlToBeDeleted)
				.then(Mono.just(FrequentUrlToBeDeleted))).log();
	}

	public Flux<FrequentUrl> allFrequentUrl() {
		return this.frequentUrlRepository.findAll().log();
	}

	public Mono<FrequentUrl> getFrequentUrlById(Integer id) {
		return frequentUrlRepository.findById(id);
	}

}
