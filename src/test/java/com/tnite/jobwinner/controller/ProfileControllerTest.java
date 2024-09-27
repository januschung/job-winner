package com.tnite.jobwinner.controller;

import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
	void testUpdateProfile() {
		Profile profile = new Profile();
		when(profileService.updateProfile(any(Profile.class))).thenReturn(Mono.just(profile));

		Mono<Profile> result = profileController.updateProfile(profile);

		assertEquals(profile, result.block());
	}

	@Test
	void getProfile() {
		Profile profile1 = new Profile();
		when(profileService.getProfile(anyInt())).thenReturn(Mono.just(profile1));

		Mono<Profile> result = profileController.profileById(1);

		assertEquals(profile1, result.block());
	}
}
