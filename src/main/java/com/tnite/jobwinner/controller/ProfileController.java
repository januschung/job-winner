package com.tnite.jobwinner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.tnite.jobwinner.model.AddProfileInput;
import com.tnite.jobwinner.model.Profile;
import com.tnite.jobwinner.service.ProfileService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    
    @MutationMapping
    public Mono<Profile> addProfile(@Argument AddProfileInput addProfileInput) {
        return profileService.addProfile(addProfileInput);
    }
    
    @MutationMapping
    public Mono<Profile> updateProfile(@Argument Profile profile) {
        return profileService.updateProfile(profile);
    }
    
    @QueryMapping
    public Flux<Profile> allProfile() {
        return profileService.allProfile();
    }

    @QueryMapping
    public Mono<Profile> profileById(@Argument Integer id) {
        return profileService.getProfile(id);
    }

}