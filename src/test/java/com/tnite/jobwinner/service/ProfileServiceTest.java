package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.AddProfileInput;
import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.repo.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
public class ProfileServiceTest {

	@Mock
	private ProfileRepository profileRepository;

	@InjectMocks
	private ProfileService profileService;

	private Profile profile1;
	private Profile profile2;
	private AddProfileInput addProfileInput1;
	private AddProfileInput addProfileInput2;
	private List<Profile> profileList;

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

		profile2 = new Profile();
		profile2.setId(2);
		profile2.setFirstName("Jane");
		profile2.setLastName("Smith");
		profile2.setAddressStreet1("456 Elm St");
		profile2.setAddressStreet2("Suite 5");
		profile2.setAddressCity("Town");
		profile2.setAddressState("Province");
		profile2.setAddressZip("67890");
		profile2.setLinkedin("jane.smith");
		profile2.setGithub("janesmith");
		profile2.setPersonalWebsite("www.janesmith.com");

		addProfileInput1 = new AddProfileInput();
		addProfileInput1.setFirstName("John");
		addProfileInput1.setLastName("Doe");
		addProfileInput1.setAddressStreet1("123 Main St");
		addProfileInput1.setAddressStreet2("Apt 4");
		addProfileInput1.setAddressCity("City");
		addProfileInput1.setAddressState("State");
		addProfileInput1.setAddressZip("12345");
		addProfileInput1.setLinkedin("john.doe");
		addProfileInput1.setGithub("johndoe");
		addProfileInput1.setPersonalWebsite("www.johndoe.com");

		addProfileInput2 = new AddProfileInput();
		addProfileInput2.setFirstName("Jane");
		addProfileInput2.setLastName("Smith");
		addProfileInput2.setAddressStreet1("456 Elm St");
		addProfileInput2.setAddressStreet2("Suite 5");
		addProfileInput2.setAddressCity("Town");
		addProfileInput2.setAddressState("Province");
		addProfileInput2.setAddressZip("67890");
		addProfileInput2.setLinkedin("jane.smith");
		addProfileInput2.setGithub("janesmith");
		addProfileInput2.setPersonalWebsite("www.janesmith.com");

		profileList = Arrays.asList(profile1, profile2);
	}

	@Test
	void testAddProfile() {
		when(profileRepository.save(ArgumentMatchers.any(Profile.class))).thenReturn(Mono.just(profile1));

		Mono<Profile> result = profileService.addProfile(addProfileInput1);

		StepVerifier.create(result)
			.assertNext(savedProfile -> {
				assertEquals(profile1.getId(), savedProfile.getId());
				assertEquals(profile1.getFirstName(), savedProfile.getFirstName());
				assertEquals(profile1.getLastName(), savedProfile.getLastName());
				assertEquals(profile1.getAddressStreet1(), savedProfile.getAddressStreet1());
				assertEquals(profile1.getAddressStreet2(), savedProfile.getAddressStreet2());
				assertEquals(profile1.getAddressCity(), savedProfile.getAddressCity());
				assertEquals(profile1.getAddressState(), savedProfile.getAddressState());
				assertEquals(profile1.getAddressZip(), savedProfile.getAddressZip());
				assertEquals(profile1.getLinkedin(), savedProfile.getLinkedin());
				assertEquals(profile1.getGithub(), savedProfile.getGithub());
				assertEquals(profile1.getPersonalWebsite(), savedProfile.getPersonalWebsite());
			})
			.verifyComplete();

		verify(profileRepository, times(1)).save(any(Profile.class));
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
	void testAllProfile() {
		when(profileRepository.findAll()).thenReturn(Flux.fromIterable(profileList));

		Flux<Profile> result = profileService.allProfile();

		StepVerifier.create(result)
			.expectNext(profile1)
			.expectNext(profile2)
			.verifyComplete();

		verify(profileRepository, times(1)).findAll();
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
