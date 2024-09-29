package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.repo.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

	@Mock
	private ProfileRepository profileRepository;

	@InjectMocks
	private ProfileService profileService;

	private Profile profile1;

	@BeforeEach
	void setUp() {
		profile1 = new Profile();
		profile1.setId(1);
		profile1.setFirstName("John");
		profile1.setLastName("Doe");
		profile1.setAddressStreet1("123 Main St");
		profile1.setAddressStreet2("Apt 4");
		profile1.setAddressCity("City");
		profile1.setAddressState("State");
		profile1.setAddressZip("12345");
		profile1.setLinkedin("john.doe");
		profile1.setGithub("johndoe");
		profile1.setPersonalWebsite("www.johndoe.com");
	}

	@Test
	void testUpdateProfile() {
		when(profileRepository.findById(1)).thenReturn(Mono.just(profile1));
		when(profileRepository.save(any(Profile.class))).thenReturn(Mono.just(profile1));

		Mono<Profile> result = profileService.updateProfile(profile1);

		StepVerifier.create(result)
			.assertNext(updatedProfile -> {
				assertEquals(profile1.getId(), updatedProfile.getId());
				assertEquals(profile1.getFirstName(), updatedProfile.getFirstName());
				assertEquals(profile1.getLastName(), updatedProfile.getLastName());
				assertEquals(profile1.getAddressStreet1(), updatedProfile.getAddressStreet1());
				assertEquals(profile1.getAddressStreet2(), updatedProfile.getAddressStreet2());
				assertEquals(profile1.getAddressCity(), updatedProfile.getAddressCity());
				assertEquals(profile1.getAddressState(), updatedProfile.getAddressState());
				assertEquals(profile1.getAddressZip(), updatedProfile.getAddressZip());
				assertEquals(profile1.getLinkedin(), updatedProfile.getLinkedin());
				assertEquals(profile1.getGithub(), updatedProfile.getGithub());
				assertEquals(profile1.getPersonalWebsite(), updatedProfile.getPersonalWebsite());
			})
			.verifyComplete();

		verify(profileRepository, times(1)).findById(1);
		verify(profileRepository, times(1)).save(any(Profile.class));
	}

	@Test
	void testGetProfile() {
		when(profileRepository.findById(1)).thenReturn(Mono.just(profile1));

		Mono<Profile> result = profileService.getProfile(1);

		StepVerifier.create(result)
			.assertNext(retrievedProfile -> {
				assertEquals(profile1.getId(), retrievedProfile.getId());
				assertEquals(profile1.getFirstName(), retrievedProfile.getFirstName());
				assertEquals(profile1.getLastName(), retrievedProfile.getLastName());
				assertEquals(profile1.getAddressStreet1(), retrievedProfile.getAddressStreet1());
				assertEquals(profile1.getAddressStreet2(), retrievedProfile.getAddressStreet2());
				assertEquals(profile1.getAddressCity(), retrievedProfile.getAddressCity());
				assertEquals(profile1.getAddressState(), retrievedProfile.getAddressState());
				assertEquals(profile1.getAddressZip(), retrievedProfile.getAddressZip());
				assertEquals(profile1.getLinkedin(), retrievedProfile.getLinkedin());
				assertEquals(profile1.getGithub(), retrievedProfile.getGithub());
				assertEquals(profile1.getPersonalWebsite(), retrievedProfile.getPersonalWebsite());
			})
			.verifyComplete();

		verify(profileRepository, times(1)).findById(1);
	}

	@Test
	void testGetProfileWhenProfileNotFound() {
		when(profileRepository.findById(3)).thenReturn(Mono.empty());

		Mono<Profile> result = profileService.getProfile(3);

		StepVerifier.create(result)
			.expectNextCount(0)
			.verifyComplete();

		verify(profileRepository, times(1)).findById(3);
	}
}
