package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.FrequentUrl;
import com.tnite.jobwinner.model.FrequentUrlInput;
import com.tnite.jobwinner.service.FrequentUrlService;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class FrequentUrlController {

	@Autowired
	private FrequentUrlService frequentUrlService;

	@MutationMapping
	public Mono<FrequentUrl> addFrequentUrl(@Argument FrequentUrlInput frequentUrlInput) {
		return frequentUrlService.addFrequentUrl(frequentUrlInput);
	}

	@MutationMapping
	public Mono<FrequentUrl> updateFrequentUrl(@Argument FrequentUrl frequentUrl) {
		return frequentUrlService.updateFrequentUrl(frequentUrl);
	}

	@MutationMapping
	public Mono<FrequentUrl> deleteFrequentUrl(@Argument @NonNull Integer id) {
		return frequentUrlService.deleteFrequentUrl(id);
	}

	@QueryMapping
	public Flux<FrequentUrl> allFrequentUrl() {
		return frequentUrlService.allFrequentUrl();
	}

	@QueryMapping
	public Mono<FrequentUrl> frequentUrlById(@Argument Integer id) {
		return frequentUrlService.getFrequentUrlById(id);
	}

}