package com.tnite.jobwinner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnite.jobwinner.model.AddProfileInput;
import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.repo.ProfileRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    private Profile mapToProfile(AddProfileInput addProfileInput) {
        var profile = new Profile();
        profile.setFirstName(addProfileInput.getFirstName());
        profile.setLastName(addProfileInput.getLastName());
        profile.setAddressStreet1(addProfileInput.getAddressStreet1());
        profile.setAddressStreet2(addProfileInput.getAddressStreet2());
        profile.setAddressCity(addProfileInput.getAddressCity());
        profile.setAddressState(addProfileInput.getAddressState());
        profile.setAddressZip(addProfileInput.getAddressZip());
        profile.setLinkedin(addProfileInput.getLinkedin());
        profile.setGithub(addProfileInput.getGithub());
        profile.setPersonalWebsite(addProfileInput.getPersonalWebsite());
        return profile;
    }

    public Mono<Profile> addProfile(AddProfileInput addProfileInput) {
        Profile profile = mapToProfile(addProfileInput);
        return profileRepository.save(profile)
            .doOnSuccess(p -> log.info("Added new profile: {}", p))
            .doOnError(e -> log.error("Failed to add profile: {}", addProfileInput, e));
    }

    public Mono<Profile> updateProfile(Profile profile) {
        return profileRepository.findById(profile.getId())
            .flatMap(existingProfile -> {
                updateProfileDetails(existingProfile, profile);
                return profileRepository.save(existingProfile);
            })
            .doOnSuccess(p -> log.info("Updated profile: {}", p))
            .doOnError(e -> log.error("Failed to update profile: {}", profile, e));
    }

    private void updateProfileDetails(Profile existingProfile, Profile updatedProfile) {
        existingProfile.setFirstName(updatedProfile.getFirstName());
        existingProfile.setLastName(updatedProfile.getLastName());
        existingProfile.setAddressStreet1(updatedProfile.getAddressStreet1());
        existingProfile.setAddressStreet2(updatedProfile.getAddressStreet2());
        existingProfile.setAddressCity(updatedProfile.getAddressCity());
        existingProfile.setAddressState(updatedProfile.getAddressState());
        existingProfile.setAddressZip(updatedProfile.getAddressZip());
        existingProfile.setLinkedin(updatedProfile.getLinkedin());
        existingProfile.setGithub(updatedProfile.getGithub());
        existingProfile.setPersonalWebsite(updatedProfile.getPersonalWebsite());
    }

    public Flux<Profile> allProfile() {
        return profileRepository.findAll()
            .doOnComplete(() -> log.info("Retrieved all profiles"))
            .doOnError(e -> log.error("Failed to retrieve profiles", e));
    }

    public Mono<Profile> getProfile(Integer id) {
        return profileRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> {
                log.warn("Profile with id {} not found", id);
                return Mono.empty();
            }))
            .doOnSuccess(profile -> log.info("Retrieved profile: {}", profile))
            .doOnError(e -> log.error("Failed to retrieve profile with id {}", id, e));
    }

}
