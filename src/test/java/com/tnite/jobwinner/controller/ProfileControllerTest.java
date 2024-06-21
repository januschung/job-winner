package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.AddProfileInput;
import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.service.ProfileService;
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
class ProfileControllerTest {

	@InjectMocks
	private ProfileController profileController;

	@Mock
	private ProfileService profileService;

	@Test
	void testAddProfile() {
		AddProfileInput addProfileInput = new AddProfileInput();
		Profile profile = new Profile();
		when(profileService.addProfile(any(AddProfileInput.class))).thenReturn(Mono.just(profile));

		Mono<Profile> result = profileController.addProfile(addProfileInput);

		assertEquals(profile, result.block());
	}

	@Test
	void testUpdateProfile() {
		Profile profile = new Profile();
		when(profileService.updateProfile(any(Profile.class))).thenReturn(Mono.just(profile));

		Mono<Profile> result = profileController.updateProfile(profile);

		assertEquals(profile, result.block());
	}

	@Test
	void testAllProfile() {
		Profile profile1 = new Profile();
		Profile profile2 = new Profile();
		when(profileService.allProfile()).thenReturn(Flux.just(profile1, profile2));

		Flux<Profile> result = profileController.allProfile();

		assertEquals(2, result.collectList().block().size());
		assertEquals(profile1, result.collectList().block().get(0));
		assertEquals(profile2, result.collectList().block().get(1));
	}

	@Test
	void testAllProfileWhenNoProfilesThenReturnEmptyResult() {
		when(profileService.allProfile()).thenReturn(Flux.empty());

		Flux<Profile> result = profileController.allProfile();

		assertEquals(0, result.collectList().block().size());
	}

	@Test
	void getProfile() {
		Profile profile1 = new Profile();
		when(profileService.getProfile(anyInt())).thenReturn(Mono.just(profile1));

		Mono<Profile> result = profileController.getProfile(1);

		assertEquals(profile1, result.block());
	}
}
