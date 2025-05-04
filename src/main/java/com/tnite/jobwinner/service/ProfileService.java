package com.tnite.jobwinner.service;

import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.repo.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

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
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setTelephone(updatedProfile.getTelephone());
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
