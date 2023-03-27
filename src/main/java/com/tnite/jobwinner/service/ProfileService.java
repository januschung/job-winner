package com.tnite.jobwinner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnite.jobwinner.model.AddProfileInput;
import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.repo.ProfileRepository;

import graphql.com.google.common.base.Function;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    Function<AddProfileInput, Profile> mapping = p -> {
        var profile = new Profile();
        profile.setFirstName(p.getFirstName());
        profile.setLastName(p.getLastName());
        profile.setAddressStreet1(p.getAddressStreet1());
        profile.setAddressStreet2(p.getAddressStreet2());
        profile.setAddressCity(p.getAddressCity());
        profile.setAddressState(p.getAddressState());
        profile.setAddressZip(p.getAddressZip());
        profile.setLinkedin(p.getLinkedin());
        profile.setGithub(p.getGithub());
        profile.setPersonalWebsite(p.getPersonalWebsite());
        return profile;
    };
    
    Function<Profile, Profile> editMapping = p -> {
        var profile = new Profile();
        profile.setId(p.getId());
        profile.setFirstName(p.getFirstName());
        profile.setLastName(p.getLastName());
        profile.setAddressStreet1(p.getAddressStreet1());
        profile.setAddressStreet2(p.getAddressStreet2());
        profile.setAddressCity(p.getAddressCity());
        profile.setAddressState(p.getAddressState());
        profile.setAddressZip(p.getAddressZip());
        profile.setLinkedin(p.getLinkedin());
        profile.setGithub(p.getGithub());
        profile.setPersonalWebsite(p.getPersonalWebsite());
        return profile;
    };
    
    
    public Mono<Profile> addProfile(AddProfileInput addProfileInput) {
        Mono<Profile> profile = profileRepository.save(mapping.apply(addProfileInput));
        log.info("Added new profile: {}", addProfileInput);
        return profile;
    }
    

    public Mono<Profile> updateProfile(Profile profile) {
        log.info("Updating profile id {}, {}", profile.getId());
        return this.profileRepository.findById(profile.getId())
                .flatMap(p -> {
                    p.setFirstName(profile.getFirstName());
                    p.setLastName(profile.getLastName());
                    p.setAddressStreet1(profile.getAddressStreet1());
                    p.setAddressStreet2(profile.getAddressStreet2());
                    p.setAddressCity(profile.getAddressCity());
                    p.setAddressState(profile.getAddressState());
                    p.setAddressZip(profile.getAddressZip());
                    p.setLinkedin(profile.getLinkedin());
                    p.setGithub(profile.getGithub());
                    p.setPersonalWebsite(profile.getPersonalWebsite());
                    return profileRepository.save(profile).log();
                });
    }
    

    public Flux<Profile> allProfile() {
        return this.profileRepository.findAll().log();
    }


}
