package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.FrequentUrl;
import com.tnite.jobwinner.model.FrequentUrlInput;
import com.tnite.jobwinner.repo.FrequentUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FrequentUrlServiceTest {

	@Mock
	private FrequentUrlRepository frequentUrlRepository;

	@InjectMocks
	private FrequentUrlService frequentUrlService;

	private FrequentUrl frequentUrl1;
	private FrequentUrl frequentUrl2;
	private FrequentUrlInput addFrequentUrlInput1;
	private List<FrequentUrl> frequentUrlList;

	@BeforeEach
	void setUp() {
		frequentUrl1 = new FrequentUrl();
		frequentUrl1.setId(1);
		frequentUrl1.setTitle("Foo");
		frequentUrl1.setUrl("https://foo.com");

		frequentUrl2 = new FrequentUrl();
		frequentUrl2.setId(2);
		frequentUrl2.setTitle("Bar");
		frequentUrl2.setUrl("https://bar.com");

		addFrequentUrlInput1 = new FrequentUrlInput();
		addFrequentUrlInput1.setTitle("Foo Edited");
		addFrequentUrlInput1.setUrl("https://foo.com/edited");

		frequentUrlList = Arrays.asList(frequentUrl1, frequentUrl2);
	}

	@Test
	void testMapping() {
		FrequentUrlInput input = new FrequentUrlInput();
		input.setTitle("Foo");
		input.setUrl("https://foo.com");

		FrequentUrl result = frequentUrlService.mapping.apply(input);

		assertEquals(input.getTitle(), result.getTitle());
		assertEquals(input.getUrl(), result.getUrl());
	}

	@Test
	void testAddFrequentUrl() {
		when(frequentUrlRepository.save(any(FrequentUrl.class))).thenReturn(Mono.just(frequentUrl1));

		Mono<FrequentUrl> result = frequentUrlService.addFrequentUrl(addFrequentUrlInput1);

		StepVerifier.create(result)
			.assertNext(savedfrequentUrl -> {
				assertEquals(frequentUrl1.getId(), savedfrequentUrl.getId());
				assertEquals(frequentUrl1.getTitle(), savedfrequentUrl.getTitle());
				assertEquals(frequentUrl1.getUrl(), savedfrequentUrl.getUrl());
			})
			.verifyComplete();

		verify(frequentUrlRepository, times(1)).save(any(FrequentUrl.class));
	}

	@Test
	void testUpdateFrequentUrl() {
		when(frequentUrlRepository.findById(1)).thenReturn(Mono.just(frequentUrl1));
		when(frequentUrlRepository.save(any(FrequentUrl.class))).thenReturn(Mono.just(frequentUrl1));

		Mono<FrequentUrl> result = frequentUrlService.updateFrequentUrl(frequentUrl1);

		StepVerifier.create(result)
			.assertNext(updatedfrequentUrl -> {
				assertEquals(frequentUrl1.getId(), updatedfrequentUrl.getId());
				assertEquals(frequentUrl1.getTitle(), updatedfrequentUrl.getTitle());
				assertEquals(frequentUrl1.getUrl(), updatedfrequentUrl.getUrl());
			})
			.verifyComplete();

		verify(frequentUrlRepository, times(1)).findById(1);
		verify(frequentUrlRepository, times(1)).save(any(FrequentUrl.class));
	}

	@Test
	void testDeleteFrequentUrlWhenFrequentUrlExistsThenReturnsFrequentUrl() {
		when(frequentUrlRepository.findById(1)).thenReturn(Mono.just(frequentUrl1));
		when(frequentUrlRepository.delete(frequentUrl1)).thenReturn(Mono.empty());

		Mono<FrequentUrl> result = frequentUrlService.deleteFrequentUrl(1);

		StepVerifier.create(result)
			.expectNextMatches(frequentUrl -> frequentUrl1.getTitle().equals("Foo"))
			.verifyComplete();

		verify(frequentUrlRepository, times(1)).findById(1);
		verify(frequentUrlRepository, times(1)).delete(frequentUrl1);
	}

	@Test
	void testDeleteFrequentUrlWhenFrequentUrlNotExists() {
		when(frequentUrlRepository.findById(1)).thenReturn(Mono.empty());

		Mono<FrequentUrl> result = frequentUrlService.deleteFrequentUrl(1);

		StepVerifier.create(result)
			.verifyComplete();

		verify(frequentUrlRepository, times(1)).findById(1);
		verifyNoMoreInteractions(frequentUrlRepository);
	}

	@Test
	void testAllFrequentUrl() {
		when(frequentUrlRepository.findAll()).thenReturn(Flux.fromIterable(frequentUrlList));

		Flux<FrequentUrl> result = frequentUrlService.allFrequentUrl();

		StepVerifier.create(result)
			.expectNext(frequentUrl1)
			.expectNext(frequentUrl2)
			.verifyComplete();

		verify(frequentUrlRepository, times(1)).findAll();
	}

}
