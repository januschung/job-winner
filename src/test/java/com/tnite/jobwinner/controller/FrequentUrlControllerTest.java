package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.FrequentUrl;
import com.tnite.jobwinner.model.FrequentUrlInput;
import com.tnite.jobwinner.service.FrequentUrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FrequentUrlControllerTest {

	@InjectMocks
	private FrequentUrlController frequentUrlController;

	@Mock
	private FrequentUrlService frequentUrlService;

	@Test
	void testAddFrequentUrl() {
		FrequentUrlInput addFrequentUrlInput = new FrequentUrlInput();
		FrequentUrl frequentUrl = new FrequentUrl();
		when(frequentUrlService.addFrequentUrl(any(FrequentUrlInput.class))).thenReturn(Mono.just(frequentUrl));

		Mono<FrequentUrl> result = frequentUrlController.addFrequentUrl(addFrequentUrlInput);

		assertEquals(frequentUrl, result.block());
	}

	@Test
	void testUpdateFrequentUrl() {
		FrequentUrl frequentUrl = new FrequentUrl();
		when(frequentUrlService.updateFrequentUrl(any(FrequentUrl.class))).thenReturn(Mono.just(frequentUrl));

		Mono<FrequentUrl> result = frequentUrlController.updateFrequentUrl(frequentUrl);

		assertEquals(frequentUrl, result.block());
	}

	@Test
	void testDeleteFrequentUrl() {
		FrequentUrl frequentUrl = new FrequentUrl();
		when(frequentUrlService.deleteFrequentUrl(anyInt())).thenReturn(Mono.just(frequentUrl));

		Mono<FrequentUrl> result = frequentUrlController.deleteFrequentUrl(1);

		assertEquals(frequentUrl, result.block());
	}

	@Test
	void testAllFrequentUrl() {
		FrequentUrl frequentUrl1 = new FrequentUrl();
		FrequentUrl frequentUrl2 = new FrequentUrl();
		when(frequentUrlService.allFrequentUrl()).thenReturn(Flux.just(frequentUrl1, frequentUrl2));

		Flux<FrequentUrl> result = frequentUrlController.allFrequentUrl();

		assertEquals(2, result.collectList().block().size());
		assertEquals(frequentUrl1, result.collectList().block().get(0));
		assertEquals(frequentUrl2, result.collectList().block().get(1));
	}

	@Test
	void testAllFrequentUrlWhenNoFrequentUrlsThenReturnEmptyResult() {
		when(frequentUrlService.allFrequentUrl()).thenReturn(Flux.empty());

		Flux<FrequentUrl> result = frequentUrlService.allFrequentUrl();

		assertEquals(0, result.collectList().block().size());
	}

}
